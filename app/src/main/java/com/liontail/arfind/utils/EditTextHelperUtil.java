package com.liontail.arfind.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextHelperUtil {
    public static void setupVerificationCodeInputs(EditText[] editTexts, Button btnIngresar) {
        for (EditText editText : editTexts) {
            addTextWatcher(editText, editTexts, btnIngresar);
        }
    }
    private static void addTextWatcher(EditText editText, EditText[] allEditTexts, Button btnIngresar) {
        editText.addTextChangedListener(createTextWatcher(allEditTexts, btnIngresar));
    }
    private static TextWatcher createTextWatcher(EditText[] editTexts, Button btnIngresar) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean allFieldsFilled = true;

                for (EditText editText : editTexts) {
                    allFieldsFilled = allFieldsFilled && !TextUtils.isEmpty(editText.getText().toString());
                }
                btnIngresar.setEnabled(allFieldsFilled);
            }
        };
    }
    public static void setupEditTexts(EditText[] editTexts) {
        for (int i = 0; i < editTexts.length; i++) {
            final int index = i;

            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (count == 1 && index < editTexts.length - 1) {
                        editTexts[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            editTexts[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        editTexts[index].setSelection(editTexts[index].getText().length());
                    }
                }
            });
        }
    }
}
