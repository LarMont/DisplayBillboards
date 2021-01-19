package com.example.displaybillboards.utilities.functions

import android.content.Context
import android.view.LayoutInflater
import android.view.View

fun View.getLayoutInflater() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater