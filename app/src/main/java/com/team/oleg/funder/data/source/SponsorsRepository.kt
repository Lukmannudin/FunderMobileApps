package com.team.oleg.funder.data.source

import com.team.oleg.funder.utils.EspressoIdlingResource
import com.team.oleg.funder.data.Sponsor

class SponsorsRepository (
    val userRemoteDataSource : SponsorsDataSource,
    val userLocalDataSource : SponsorsDataSource
): SponsorsDataSource{


    var cachedSponsors : LinkedHashMap<String, Sponsor> = LinkedHashMap()

    var cacheIsDirty = false


    /**
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     *
     *
     * Note: [LoadUsersCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getSponsors(callback: SponsorsDataSource.LoadSponsorsCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedSponsors.isNotEmpty() && !cacheIsDirty){
            callback.onSponsorsLoaded(ArrayList(cachedSponsors.values))
            return
        }

        EspressoIdlingResource.increment()

        if (cacheIsDirty){
            // If the cache is dirty we need to fetch new data from the network.
            getTasksFromRemoteDataSource(callback)
        } else {
            // Query the local storage if available. If not, query the network.
            userLocalDataSource.getSponsors(object : SponsorsDataSource.LoadSponsorsCallback{
                override fun onSponsorsLoaded(sponsor: List<Sponsor>) {
                    refreshCache(sponsor)
                    EspressoIdlingResource.decrement()
                    callback.onSponsorsLoaded(ArrayList(cachedSponsors.values))
                }

                override fun onDataNotAvailable() {
                    EspressoIdlingResource.decrement()
                    callback.onDataNotAvailable()
                }
            })
        }
    }

    override fun getSponsor(userid: String, callback: SponsorsDataSource.GetSponsorCallback) {
        val userInCache = getUserById(userid)

        // Respond immediately with cache if available
        if (userInCache != null){
            callback.onSponsorLoaded(userInCache)
            return
        }

        EspressoIdlingResource.increment()

        // Load from server/persisted if needed

        // Is the task in the local data source? If not, query the network
        userLocalDataSource.getSponsor(userid, object : SponsorsDataSource.GetSponsorCallback{
            override fun onSponsorLoaded(sponsor: Sponsor) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(sponsor){
                    EspressoIdlingResource.decrement()
                    callback.onSponsorLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                userRemoteDataSource.getSponsor(userid, object : SponsorsDataSource.GetSponsorCallback{
                    override fun onSponsorLoaded(sponsor: Sponsor) {
                        // Do in memory cache update to keep the app UI up to date
                        cacheAndPerform(sponsor){
                            EspressoIdlingResource.decrement() // Set app as idle
                            callback.onSponsorLoaded(it)
                        }
                    }

                    override fun onDataNotAvailable() {
                        EspressoIdlingResource.decrement() // Set app as idle
                        callback.onDataNotAvailable()
                    }
                })
            }
        })

    }


    override fun refreshSponsors() {
        cacheIsDirty = true
    }

    override fun deleteAllSponsors() {
        userRemoteDataSource.deleteAllSponsors()
        userLocalDataSource.deleteAllSponsors()
        cachedSponsors.clear()
    }

    override fun deleteUserSponsor(sponsorid: String) {
        userRemoteDataSource.deleteUserSponsor(sponsorid)
        userLocalDataSource.deleteUserSponsor(sponsorid)
        cachedSponsors.remove(sponsorid)
    }

    private fun getTasksFromRemoteDataSource(callback:SponsorsDataSource.LoadSponsorsCallback){
        userRemoteDataSource.getSponsors(object : SponsorsDataSource.LoadSponsorsCallback {
            override fun onSponsorsLoaded(sponsor: List<Sponsor>) {
                refreshCache(sponsor)
                refreshLocalDataSource(sponsor)

                EspressoIdlingResource.decrement() // Set app as idle
                callback.onSponsorsLoaded(ArrayList(cachedSponsors.values))
            }

            override fun onDataNotAvailable() {
                EspressoIdlingResource.decrement() // Set app as idle
                callback.onDataNotAvailable()
            }
        })
    }

    fun refreshCache(users: List<Sponsor>){
        cachedSponsors.clear()
        users.forEach {
            cacheAndPerform(it){}
        }
        cacheIsDirty = false
    }

    fun refreshLocalDataSource(users: List<Sponsor>){
        userLocalDataSource.deleteAllSponsors()
        for (user in users){
            userLocalDataSource.saveSponsor(user)
        }
    }

    fun getUserById(id:String) = cachedSponsors[id]

    private inline fun cacheAndPerform(sponsor: Sponsor, perform: (Sponsor) -> Unit){
//        val cachedUser =
//           Sponsor(
//
//           )
//        cachedSponsors[cachedUser.eoId!!] = cachedUser
//        perform(cachedUser)
    }

    override fun saveSponsor(user: Sponsor) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(user){
            userRemoteDataSource.saveSponsor(user)
            userLocalDataSource.saveSponsor(user)
        }
    }

    companion object {

        private var INSTANCE: SponsorsRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         * @param tasksRemoteDataSource the backend data source
         * *
         * @param tasksLocalDataSource  the device storage data source
         * *
         * @return the [TasksRepository] instance
         */
        @JvmStatic fun getInstance(sponsorsRemoteDataSource: SponsorsDataSource,
                                   sponsorsLocalDataSource: SponsorsDataSource)=
                INSTANCE ?: synchronized(SponsorsRepository::class.java){
                    INSTANCE ?: SponsorsRepository(sponsorsRemoteDataSource, sponsorsLocalDataSource)
                        .also { INSTANCE = it }
                }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance(){
            INSTANCE = null
        }
    }
}