package com.example.lab24

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity
data class myAppEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title: String,
    val description: String?,
    val date: Long = System.currentTimeMillis()
)

@Dao
interface  myAppDao{
    @Insert
    suspend fun insert(myApp: myAppEntity)

    @Query("SELECT  * FROM myAppEntity")
    fun getAll(): Flow<List<myAppEntity>>

    @Delete
    suspend fun delete(myAppEntity: myAppEntity)

    @Update
    suspend fun update(myAppEntity: myAppEntity)
}

@Database(
    entities = [myAppEntity::class],
    version = 1
)
abstract class  AppDatabase : RoomDatabase(){
    abstract fun myappDao(): myAppDao
    companion object{
        @Volatile
        private  var INSTANCE: AppDatabase? =  null
        fun getDatabase(context: Context) : AppDatabase{
            return  INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "myappdb"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

class MyRepository(private  val dao: myAppDao) {
    suspend fun insert(myAppEntity: myAppEntity) {
        dao.insert(myAppEntity)
    }

    suspend fun delete(myAppEntity: myAppEntity) {
        dao.delete(myAppEntity)
    }

    suspend fun update(myAppEntity: myAppEntity) {
        dao.update(myAppEntity)
    }
    val MyappAll = dao.getAll()


}

class  MyAppviewModel(
    private  val repository: MyRepository
): ViewModel(){

    val Myappall = repository.MyappAll

    var selectionNote by mutableStateOf<myAppEntity?>(null)

    fun selectNoteForEdit(note: myAppEntity?){
        selectionNote = note
    }

    fun saveNote(title: String,description: String?,date: Long) {
        viewModelScope.launch {
            val current = selectionNote
            if(current == null) {
                repository.insert(
                    myAppEntity(
                        title = title,
                        description =  description,
                        date =  date
                    )
                )
            } else {
                repository.update(
                    current.copy(
                        title = title,
                        description =  description,
                        date = date
                    )
                )
            }
            selectionNote = null
        }
    }

    fun insertMyapp(title: String,description: String?,date: Long?) {
        viewModelScope.launch {
            repository.insert(
                myAppEntity(
                    title = title,
                    description = description,
                    date = date ?: System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteMyApp(myAppEntity: myAppEntity) {
        viewModelScope.launch {
            repository.delete(myAppEntity)
        }
    }

    fun updateMyapp(id: Int,title: String,description: String?,date: Long) {
        viewModelScope.launch {
            repository.update(
                myAppEntity(
                    id = id,
                    title = title,
                    description = description,
                    date = date
                )
            )
        }
    }
}

class MyappviewModelFactory(context: Context) : ViewModelProvider.Factory{
    private  val dao  = AppDatabase.getDatabase(context).myappDao()
    private  val repository = MyRepository(dao)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyAppviewModel::class.java)) {
            return MyAppviewModel(repository) as T
        }
        throw IllegalArgumentException("not fount it you want ")
    }
}
