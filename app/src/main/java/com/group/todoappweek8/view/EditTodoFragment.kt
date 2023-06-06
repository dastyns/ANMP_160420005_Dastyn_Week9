package com.group.todoappweek8.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.group.todoappweek8.R
import com.group.todoappweek8.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        view.findViewById<TextView>(R.id.txtJudulToDo).text = "Edit Todo"
        view.findViewById<Button>(R.id.btnAdd).text = "Save Changes"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        val btnCreateTodo = view.findViewById<Button>(R.id.btnAdd)
        val txtTitle = view.findViewById<TextInputEditText>(R.id.txtTitle)
        val txtNotes = view.findViewById<TextInputEditText>(R.id.txtNotes)

        btnCreateTodo.setOnClickListener {
            val radio =
                view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            val radioBtn = view.findViewById<RadioButton>(radio.checkedRadioButtonId)
            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(),
                radioBtn.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }


    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            view?.findViewById<EditText>(R.id.txtTitle)?.setText(it.title)
            view?.findViewById<EditText>(R.id.txtNotes)?.setText(it.notes)

           val radioLow = view?.findViewById<RadioButton>(R.id.radioLow)
            val radioMedium = view?.findViewById<RadioButton>(R.id.radioMedium)
            val radioHigh = view?.findViewById<RadioButton>(R.id.radioHigh)
            when (it.priority) {
                1 -> radioLow?.isChecked = true
                2 -> radioMedium?.isChecked = true
                else -> radioHigh?.isChecked = true
            }
        })
    }


}