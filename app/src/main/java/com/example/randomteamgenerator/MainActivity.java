package com.example.randomteamgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText inputNames;
    private TextView outputTeams;
    private Spinner teamCountSpinner;
    private Button biodataButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputNames = findViewById(R.id.inputNames);
        outputTeams = findViewById(R.id.outputTeams);
        teamCountSpinner = findViewById(R.id.teamCountSpinner);
        Button generateButton = findViewById(R.id.generateButton);
        Button clearButton = findViewById(R.id.clearButton);
        biodataButton = findViewById(R.id.biodataButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Set up the spinner with a default prompt and numbers 2 to 10
        String[] teamCounts = new String[]{"Pilih jumlah tim", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamCounts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teamCountSpinner.setAdapter(adapter);

        teamCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    teamCountSpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTeams();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTeams();
            }
        });

        biodataButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void generateTeams() {
        String namesInput = inputNames.getText().toString();
        if (namesInput.isEmpty()) {
            outputTeams.setText("Mohon Masukkan Nama Terlebih Dahulu");
            return;
        }

        if (teamCountSpinner.getSelectedItemPosition() == 0) {
            outputTeams.setText("Mohon Pilih Jumlah Tim");
            return;
        }

        String[] namesArray = namesInput.split(",");
        List<String> namesList = new ArrayList<>();
        for (String name : namesArray) {
            namesList.add(name.trim());
        }

        Collections.shuffle(namesList);

        int teamCount = Integer.parseInt((String) teamCountSpinner.getSelectedItem());
        List<List<String>> teams = new ArrayList<>();
        for (int i = 0; i < teamCount; i++) {
            teams.add(new ArrayList<String>());
        }

        for (int i = 0; i < namesList.size(); i++) {
            teams.get(i % teamCount).add(namesList.get(i));
        }

        StringBuilder teamsOutput = new StringBuilder();
        for (int i = 0; i < teams.size(); i++) {
            teamsOutput.append("Team ").append(i + 1).append(":\n");
            for (String member : teams.get(i)) {
                teamsOutput.append(member).append("\n");
            }
            teamsOutput.append("\n");
        }

        outputTeams.setText(teamsOutput.toString());
    }

    private void clearTeams() {
        inputNames.setText("");
        outputTeams.setText("");
    }
}
