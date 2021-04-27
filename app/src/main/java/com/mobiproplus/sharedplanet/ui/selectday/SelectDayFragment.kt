package com.mobiproplus.sharedplanet.ui.selectday

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiproplus.sharedplanet.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_select_day.*

@AndroidEntryPoint
class SelectDayFragment : Fragment() {

    private val selectDayViewModel: SelectDayViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_select_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainAdapter = SelectDayAdapter(SelectDayAdapter.NasaDateListener { date ->
            selectDayViewModel.onDateClicked(date)
        })
        val layoutManager = GridLayoutManager(context, 2)
        dateList.layoutManager = layoutManager
        dateList.adapter = mainAdapter

        selectDayViewModel.navigateToSelectTime.observe(viewLifecycleOwner, Observer { date ->
            date?.let {
                this.findNavController().navigate(
                    SelectDayFragmentDirections
                        .actionMainFragmentToSelectPhotoFragment(it)
                )
                selectDayViewModel.onSelectedTimeNavigated()
            }
        })

        selectDayViewModel.dataInfo.observe(viewLifecycleOwner, Observer { nasaDates ->
            progress.visibility = View.GONE
            mainAdapter.data = nasaDates
        })

        selectDayViewModel.toastMessage.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                progress.visibility = View.GONE
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                selectDayViewModel.onToastShown()
            }
        })
    }
}