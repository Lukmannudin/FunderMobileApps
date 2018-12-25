package com.team.oleg.funder.Chat

import android.app.ActionBar
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.team.oleg.funder.R
import kotlinx.android.synthetic.main.toolbar.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainChatFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class MainChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Fragment", "MainChat")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main_chat, container, false)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSearchView()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainChatFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MainChatFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
        @JvmStatic
        fun newInstance() = MainChatFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        actionSearch.clearFocus()
        actionSearch.setQuery("", false)
        actionSearch.isIconified = true
    }

    private fun setupSearchView() {
        val searchIconImage = actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_button)
        searchIconImage.setImageDrawable(context?.let { ContextCompat.getDrawable(it, R.drawable.icon_search) })
        tbTitle.text = getString(R.string.title_chat)
        val searchIconCloseImage =
            actionSearch.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        searchIconCloseImage.setImageDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.ic_close_white_24dp
            )
        })
        actionSearch.setOnSearchClickListener {
            tbTitle.visibility = View.GONE
            actionSearch.layoutParams.width = ActionBar.LayoutParams.MATCH_PARENT
        }

        val searchIconEditText = actionSearch.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchIconEditText.setTextColor(resources.getColor(R.color.white))

        actionSearch.setOnCloseListener {
            actionSearch.layoutParams.width = ActionBar.LayoutParams.WRAP_CONTENT
            tbTitle.visibility = View.VISIBLE
            false
        }
    }
}
