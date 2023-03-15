package jt.projects.gbandroidpro

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.testData


val intentWithTestData =
    Intent(ApplicationProvider.getApplicationContext(), DescriptionActivity::class.java)
        .putExtra(DescriptionActivity.DATA_KEY, testData)
