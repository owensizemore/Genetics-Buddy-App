package com.example.septemberhackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting up labels and TextViews
        TextView cutSiteSequenceText1 = (TextView) findViewById(R.id.firstSiteDetails);
        TextView cutSiteSequenceText2 = (TextView) findViewById(R.id.secondSiteDetails);

        // create reference to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // create reference to vectors collection
        CollectionReference vectorsRef = db.collection("vectors");

        // set up vector spinner
        Spinner vectorSpinner = (Spinner) findViewById(R.id.vectorSpinner);
        List<String> vectors = new ArrayList<>();
        ArrayAdapter<String> vectorAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, vectors);
        vectorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vectorSpinner.setAdapter(vectorAdapter);

        vectorsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if (task.isSuccessful()) {
                   for (QueryDocumentSnapshot document : task.getResult()) {
                       // get vector name and add to list for vector spinner
                       String vectorName = document.getString("name");
                       vectors.add(vectorName);
                   }
                   vectorAdapter.notifyDataSetChanged();
               }
           }
        });

        // set up first restriction site spinner
        Spinner firstSiteSpinner = (Spinner) findViewById(R.id.firstSiteSpinner);

        // set up second restriction site spinner
        Spinner secondSiteSpinner = (Spinner) findViewById(R.id.secondSiteSpinner);

        // whenever vector spinner is updated, update first restriction site spinner
        vectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();
                db.collection("vectors")
                        .whereEqualTo("name", selected)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        List<String> sites = (List<String>) document.get("restrictionSites");
                                        ArrayAdapter<String> sitesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, sites);
                                        firstSiteSpinner.setAdapter(sitesAdapter);
                                        secondSiteSpinner.setAdapter(sitesAdapter);
                                    }
                                }
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // whenever first restriction site spinner is updated, change details and search for best buffer
        firstSiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();

                // clear existing text in label, clear old list of site buffers
                cutSiteSequenceText1.setText("");

                // update labels
                db.collection("sites")
                        .whereEqualTo("name", selected)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // pull data from fields and update labels
                                        String sequenceTop = document.getString("sequenceTop");
                                        String sequenceBottom = document.getString("sequenceBottom");
                                        String diluent = document.getString("diluent");
                                        String heatInactivation = document.getString("heatInactivation");
                                        String incubationTemperature = document.getString("headIncubation");
                                        List<String> buffers = (List<String>) document.get("buffers");
                                        List<Long> bufferPercents = (List<Long>) document.get("bufferPercents");

                                        // create proper buffers list
                                        String buffersWithPercents = buffers.get(0) + ": " + bufferPercents.get(0).toString() + "% (BEST)";
                                        for (int i = 1; i < 4; i++) {
                                            buffersWithPercents += "\n" + buffers.get(i) + ": " + bufferPercents.get(i).toString() + "%";
                                        }

                                        cutSiteSequenceText1.setText(
                                                "Cut Site Sequence:\n" + sequenceTop + "\n" + sequenceBottom
                                                + "\n\nDiluent Compatibility: " + diluent
                                                + "\n\nHeat Inactivation: " + heatInactivation
                                                + "\n\nIncubation Temperature: " + incubationTemperature
                                                + "\n\nBuffer Activity:\n" + buffersWithPercents
                                        );
                                    }
                                }
                            }
                        });

                // call find best buffer
                findBestBuffer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // whenever second restriction site spinner is updated, change details and search for best buffer
        secondSiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getSelectedItem().toString();

                // clear existing text in label, clear old list of site buffers
                cutSiteSequenceText2.setText("");

                // update labels
                db.collection("sites")
                        .whereEqualTo("name", selected)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        // pull data from fields and update labels
                                        String sequenceTop = document.getString("sequenceTop");
                                        String sequenceBottom = document.getString("sequenceBottom");
                                        String diluent = document.getString("diluent");
                                        String heatInactivation = document.getString("heatInactivation");
                                        String incubationTemperature = document.getString("headIncubation");
                                        List<String> buffers = (List<String>) document.get("buffers");
                                        List<Long> bufferPercents = (List<Long>) document.get("bufferPercents");

                                        // create proper buffers list
                                        String buffersWithPercents = buffers.get(0) + ": " + bufferPercents.get(0).toString() + "% (BEST)";
                                        for (int i = 1; i < 4; i++) {
                                            buffersWithPercents += "\n" + buffers.get(i) + ": " + bufferPercents.get(i).toString() + "%";
                                        }

                                        cutSiteSequenceText2.setText(
                                                "Cut Site Sequence:\n" + sequenceTop + "\n" + sequenceBottom
                                                        + "\n\nDiluent Compatibility: " + diluent
                                                        + "\n\nHeat Inactivation: " + heatInactivation
                                                        + "\n\nIncubation Temperature: " + incubationTemperature
                                                        + "\n\nBuffer Activity:\n" + buffersWithPercents
                                        );
                                    }
                                }
                            }
                        });

                // call find best buffer
                findBestBuffer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void findBestBuffer() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView bestBufferLabel = (TextView) findViewById(R.id.bestBufferLabel);

        Spinner firstSiteSpinner = (Spinner) findViewById(R.id.firstSiteSpinner);
        String site1 = firstSiteSpinner.getSelectedItem().toString();

        Spinner secondSiteSpinner = (Spinner) findViewById(R.id.secondSiteSpinner);
        String site2 = secondSiteSpinner.getSelectedItem().toString();

        if (!site1.equals(site2)) {
            // implementation
            db.collection("sites")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<String> site1Buffers = new ArrayList<String>();
                                List<String> site2Buffers = new ArrayList<String>();
                                List<Long> site1BufferPercentages = new ArrayList<Long>();
                                List<Long> site2BufferPercentages = new ArrayList<Long>();

                                long bestPercentage = 0;
                                String bestBuffer = "";

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (document.getString("name").equals(site1)) {
                                        site1Buffers = (List<String>) document.get("buffers");
                                        site1BufferPercentages = (List<Long>) document.get("bufferPercents");
                                    } else if (document.getString("name").equals(site2)) {
                                        site2Buffers = (List<String>) document.get("buffers");
                                        site2BufferPercentages = (List<Long>) document.get("bufferPercents");
                                    }
                                }

                                if (!site1Buffers.isEmpty() && !site2Buffers.isEmpty()) {
                                    for (int i = 0; i < 4; i++) {
                                        for (int j = 0; j < 4; j++) {
                                            if (site1Buffers.get(i).equals(site2Buffers.get(j))) {
                                                if (site1BufferPercentages.get(i) + site2BufferPercentages.get(j) > bestPercentage) {
                                                    bestPercentage = Long.valueOf(site1BufferPercentages.get(i) + site2BufferPercentages.get(j));
                                                    bestBuffer = site1Buffers.get(i);
                                                }
                                            }
                                        }
                                    }
                                    bestBufferLabel.setText("The best buffer for this scenario is: " + bestBuffer);
                                }
                            }
                        }
                    });
        } else {
            bestBufferLabel.setText(getString(R.string.same_site));
        }
    }
}