package com.turing.alan.cpifp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.turing.alan.cpifp.R
import com.turing.alan.cpifp.data.InMemoryTaskRepository
import com.turing.alan.cpifp.data.Task
import com.turing.alan.cpifp.data.TaskRepository
import com.turing.alan.cpifp.databinding.FragmentTaskListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class TaskListFragment : Fragment() {

    private lateinit var repository:TaskRepository
    private lateinit var binding: FragmentTaskListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_task_list, container, false)
        binding = FragmentTaskListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TaskListAdapter(::toItemDetail,::shareTask)
        val rv = binding.tasksList
        rv.adapter = adapter
        (rv.adapter as TaskListAdapter).submitList(repository.readAll())
        binding.createTaskFab.setOnClickListener {
            val action = TaskListFragmentDirections.actionTaskListFragmentToTaskCreateFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        val rv = binding.tasksList
        (rv.adapter as TaskListAdapter).submitList(repository.readAll())
    }

    private fun toItemDetail(task: Task) {
        val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailFragment(task.id)
        findNavController().navigate(action)
    }

    private fun shareTask(task:Task) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "La tarea ${task.title} - ${task.body} se te ha asignado"
        )
        val chooser = Intent.createChooser(intent, "")
        startActivity(chooser)
    }

}