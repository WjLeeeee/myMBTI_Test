package com.example.mymbti_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment(){

    private var quesionType: Int = 0

    private val questionTitle = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )
    private var questionTexts = listOf(
        listOf(R.string.question1_1, R.string.question1_2, R.string.question1_3),
        listOf(R.string.question2_1, R.string.question2_2, R.string.question2_3),
        listOf(R.string.question3_1, R.string.question3_2, R.string.question3_3),
        listOf(R.string.question4_1, R.string.question4_2, R.string.question4_3)
    )
    private var questionAnswers = listOf(
        listOf(
            listOf(R.string.question1_1_answer1, R.string.question1_1_answer2),
            listOf(R.string.question1_2_answer1, R.string.question1_2_answer2),
            listOf(R.string.question1_3_answer1, R.string.question1_3_answer2)
        ),
        listOf(
            listOf(R.string.question2_1_answer1, R.string.question2_1_answer2),
            listOf(R.string.question2_2_answer1, R.string.question2_2_answer2),
            listOf(R.string.question2_3_answer1, R.string.question2_3_answer2)
        ),listOf(
            listOf(R.string.question3_1_answer1, R.string.question3_1_answer2),
            listOf(R.string.question3_2_answer1, R.string.question3_2_answer2),
            listOf(R.string.question3_3_answer1, R.string.question3_3_answer2)
        ),listOf(
            listOf(R.string.question4_1_answer1, R.string.question4_1_answer2),
            listOf(R.string.question4_2_answer1, R.string.question4_2_answer2),
            listOf(R.string.question4_3_answer1, R.string.question4_3_answer2)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            quesionType = it.getInt(ARG_QUESTION_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        val title: TextView = view.findViewById(R.id.tv_quesion_title)
        title.text = getString(questionTitle[quesionType])

        val questionTextView = listOf<TextView>(
            view.findViewById(R.id.tv_quesion_1),
            view.findViewById(R.id.tv_quesion_2),
            view.findViewById(R.id.tv_quesion_3)
        )
        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )

        for(i in questionTextView.indices){
            questionTextView[i].text = getString(questionTexts[quesionType][i])

            val radioButton1 = answerRadioGroup[i].getChildAt(0) as RadioButton
            val radioButton2 = answerRadioGroup[i].getChildAt(1) as RadioButton
            radioButton1.text = getString(questionAnswers[quesionType][i][0])
            radioButton2.text = getString(questionAnswers[quesionType][i][1])
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answerRadioGroup = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )
        val btn_next: Button = view.findViewById(R.id.btn_next)
        btn_next.setOnClickListener {
            val isAllanswered = answerRadioGroup.all { it.checkedRadioButtonId != -1 }
            if(isAllanswered){
                val responses = answerRadioGroup.map { radioGroup ->
                    val firstRadioButton = radioGroup.getChildAt(0)as RadioButton
                    if(firstRadioButton.isChecked)1 else 2
                }
                (activity as? TestActivity)?.QuestionnaireResults?.addResponses(responses)
                (activity as? TestActivity)?.moveToNextQuestion()
            }else{
                Toast.makeText(context, "모든 질문에 답해주세요", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object{
        private const val ARG_QUESTION_TYPE = "questionType"

        fun newInstance(quesionType: Int): QuestionFragment{
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION_TYPE, quesionType)
            fragment.arguments = args
            return fragment
        }

    }

}