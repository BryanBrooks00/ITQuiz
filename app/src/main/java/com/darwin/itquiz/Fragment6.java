package com.darwin.itquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment6#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment6 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final String TAG = "LOG";

    public Fragment6() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment6 newInstance(String param1, String param2) {
        Fragment6 fragment = new Fragment6();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_6, container, false);
        EditText answer_et = view.findViewById(R.id.answer_et);
        TextView result_tv = view.findViewById(R.id.result_tv);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        Button btn_next = view.findViewById(R.id.btn_next);
        btn_next.setVisibility(View.INVISIBLE);
        result_tv.setVisibility(View.INVISIBLE);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "btn_ok clicked");
                String rightAnswer = getResources().getString(R.string.answer6);
                String answer = answer_et.getText().toString().toLowerCase().replaceAll("\\s+","");
                if (!answer.equals("")) {
                    Log.i(TAG, "answer: " + answer);
                    result_tv.setVisibility(View.VISIBLE);
                    if (answer.equals(rightAnswer)) {
                        Log.i(TAG, "right");
                        btn_ok.setEnabled(false);
                        result_tv.setText(getResources().getString(R.string.truth));
                        result_tv.setTextColor(Color.GREEN);
                        btn_next.setVisibility(View.VISIBLE);
                    } else {
                        Log.i(TAG, "wrong");
                        answer_et.setText("");
                        result_tv.setText(getResources().getString(R.string.wrong));
                        result_tv.setTextColor(Color.RED);
                    }
                } else {
                    Log.i(TAG, "empty EditText");
                    Toast.makeText(getActivity(), getResources().getString(R.string.noText), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "btn_next clicked");
                ((MainActivity)getActivity()).savePrefs(7);
            }
        });

        return view;
    }
}