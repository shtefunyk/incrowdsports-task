package com.incrowdsports.task.ui.detail

import android.annotation.SuppressLint
import android.os.Build.ID
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.incrowdsports.task.R
import com.incrowdsports.task.databinding.FixtureDetailFragmentBinding
import com.incrowdsports.task.databinding.FixtureListFragmentBinding
import com.incrowdsports.task.model.data.DetailData
import com.incrowdsports.task.model.data.Player
import com.incrowdsports.task.util.Resource
import com.incrowdsports.task.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FixtureDetailFragment : Fragment(R.layout.fixture_detail_fragment) {

    private val binding by lazy { FixtureDetailFragmentBinding.bind(requireView()) }
    private val viewModel: FixtureDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(ID, null)?.let {
            lifecycleScope.launch {
                viewModel.loadDetailInfo(it)
            }
        }
        viewModel.fixture
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it) {
                    is Resource.Success -> {
                        it.data?.let { item ->
                            initViews(item.data)
                        }
                    }
                    is Resource.Error -> {
                        it.message?.let {
                                msg -> showToast(msg)
                        }
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(info: DetailData) {
        binding.place.text = info.competition

        val homeTeam = info.homeTeam
        val awayTeam = info.awayTeam
        binding.score.text = "${homeTeam.name} (${homeTeam.score}) - ${awayTeam.name} (${awayTeam.score})"

        initPlayers(homeTeam.players, binding.homeTeam)
        initPlayers(awayTeam.players, binding.awayTeam)
    }

    private fun initPlayers(items: List<Player>, linear: LinearLayout) {
        items.forEach {
            val item = TextView(context)
            item.text = it.known
            linear.addView(item)
        }
    }

    companion object {
        private const val ID = "ID"

        fun newInstance(id: String) = FixtureDetailFragment().apply {
            val bundle = Bundle()
            bundle.putString(ID, id)
            arguments = bundle
        }
    }

}