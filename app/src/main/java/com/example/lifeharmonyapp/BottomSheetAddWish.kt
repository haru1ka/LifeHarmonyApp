package com.example.lifeharmonyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetAddWish: BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Подключаем созданную разметку
        return inflater.inflate(R.layout.bottom_sheet_add_wish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Кнопка закрытия
        view.findViewById<View>(R.id.btnClose).setOnClickListener {
            dismiss() // Закрывает BottomSheet
        }
    }

    // Опционально: стиль для скругленных углов

}