package jt.projects.repository.datasource

import jt.projects.model.data.DataModel

interface DataSourceLocal<T> : DataSource<T> {
    suspend fun saveDataToDb(data: DataModel)
    suspend fun getAllDataFromDb(): T
    suspend fun clearDb()
}