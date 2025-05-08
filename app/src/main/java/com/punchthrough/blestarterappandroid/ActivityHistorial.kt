/*
 * Copyright 2025 Punch Through Design LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.punchthrough.blestarterappandroid

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class HistorialActivity : AppCompatActivity() {

    private lateinit var etDesde: EditText
    private lateinit var etHasta: EditText
    private lateinit var chart: LineChart
    private lateinit var gsrHistory: ArrayList<Pair<Long, Float>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        // Referencias UI
        etDesde = findViewById(R.id.etDesde)
        etHasta = findViewById(R.id.etHasta)
        chart = findViewById(R.id.gsrChart)
        val btnFiltrar = findViewById<Button>(R.id.btnFiltrar)

        // Obtener historial
        @Suppress("UNCHECKED_CAST")
        gsrHistory = intent.getSerializableExtra("gsr_history") as? ArrayList<Pair<Long, Float>> ?: arrayListOf()

        // Mostrar todos los datos al inicio
        mostrarGrafica(gsrHistory)

        // Mostrar DatePickerDialog
        etDesde.setOnClickListener { mostrarSelectorFecha(etDesde) }
        etHasta.setOnClickListener { mostrarSelectorFecha(etHasta) }

        // Filtrar por fechas
        btnFiltrar.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val desdeStr = etDesde.text.toString()
            val hastaStr = etHasta.text.toString()

            if (desdeStr.isNotEmpty() && hastaStr.isNotEmpty()) {
                val desdeMs = sdf.parse(desdeStr)?.time
                val hastaMs = sdf.parse(hastaStr)?.time

                if (desdeMs != null && hastaMs != null && desdeMs <= hastaMs) {
                    val filtrados = gsrHistory.filter { it.first in desdeMs..hastaMs }
                    mostrarGrafica(filtrados)
                }
            }
        }
    }

    private fun mostrarSelectorFecha(editText: EditText) {
        val calendario = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                editText.setText(fechaSeleccionada)
            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun mostrarGrafica(datos: List<Pair<Long, Float>>) {
        val entries = datos.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair.second)
        }

        val dataSet = LineDataSet(entries, "GSR")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.RED)
        dataSet.setDrawValues(false)

        chart.data = LineData(dataSet)
        chart.invalidate()
    }
}