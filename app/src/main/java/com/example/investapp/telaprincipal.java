package com.example.investapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class telaprincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telaprincipal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText edtValorInicial = findViewById(R.id.editTextText);
        EditText edtAporte = findViewById(R.id.editTextText2);
        EditText edtTempo = findViewById(R.id.editTextText3);
        EditText edtTaxa = findViewById(R.id.editTextText4);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        EditText edtResultado = findViewById(R.id.edtResultado);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sValor = edtValorInicial.getText().toString();
                String sAporte = edtAporte.getText().toString();
                String sTaxa = edtTaxa.getText().toString();
                String sTempo = edtTempo.getText().toString();

                // 1. Verificação de campos vazios
                if (sValor.isEmpty() || sAporte.isEmpty() || sTaxa.isEmpty() || sTempo.isEmpty()) {
                    return;
                }

                try {
                    // 2. Usar SEMPRE as strings tratadas com replace
                    double valorInicial = Double.parseDouble(sValor.replace(",", "."));
                    double aporteMensal = Double.parseDouble(sAporte.replace(",", "."));
                    double taxaMensal = Double.parseDouble(sTaxa.replace(",", ".")) ;
                    int meses = Integer.parseInt(sTempo);

                    // 3. Cálculos
                    double resultadoInicial = valorInicial * Math.pow((1 + taxaMensal), meses);
                    double resultadoAportes = 0;

                    // Evitar divisão por zero se a taxa for 0
                    if (taxaMensal > 0) {
                        resultadoAportes = aporteMensal * ((Math.pow((1 + taxaMensal), meses) - 1) / taxaMensal);
                    } else {
                        resultadoAportes = aporteMensal * meses;
                    }

                    double totalFinal = resultadoInicial + resultadoAportes;
                    double totalInvestido = valorInicial + (aporteMensal * meses);
                    double apenasJuros = totalFinal - totalInvestido;

                    // 4. Exibição formatada
                    edtResultado.setText(String.format("Total Investido: R$ %.2f\nJuros Ganhos: R$ %.2f\nTotal Geral: R$ %.2f",
                            totalInvestido, apenasJuros, totalFinal));

                } catch (Exception e) {
                    // Se ainda assim der erro (ex: o usuário digitou uma letra), o app não fecha
                    edtResultado.setText("Erro nos dados digitados.");
                }
            }
        });

    }
}