package com.app.dportshipper.view.inputDataDiri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.dportshipper.databinding.ActivityInputDataDiriBinding;
import com.app.dportshipper.databinding.ActivityTypeUsahaBinding;

public class TypeUsahaActivity extends AppCompatActivity {

    private ActivityTypeUsahaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTypeUsahaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}