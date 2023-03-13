package jt.projects.gbandroidpro

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import jt.projects.gbandroidpro.presentation.ui.description.DescriptionActivity
import jt.projects.model.data.DataModel
import jt.projects.model.data.testData


const val GOOD_QUERY = "correct query"
const val BAD_QUERY = "some incorrect query"

val intentWithTestData =
    Intent(ApplicationProvider.getApplicationContext(), DescriptionActivity::class.java)
        .putExtra(DescriptionActivity.DATA_KEY, testData)
