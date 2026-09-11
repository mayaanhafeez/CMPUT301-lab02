package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.listycity.ui.theme.ListyCityTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

class CityRepository {
  private val _cities = mutableStateListOf(
    "Edmonton", "Vancouver", "Moscow", " Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Dehli"
  )
  val cities: List<String>
  get() = _cities

  fun addCity(city:String) {
    _cities.add(city)
  }

fun deleteCity(city:String) {
  if (city in _cities) {
  _cities.remove(city)
}
}

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      val cityRepository = CityRepository()

      setContent {
        ListyCityTheme {
          Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CityListScreen(
              cities = cityRepository.cities,
              onRemoveCity = {cityRepository.deleteCity(it)},
              onAddCity ={cityRepository.addCity(it)},
              modifier = Modifier.padding(innerPadding)
            )
          }
        }
      }
    }
}


@Composable
fun CityRow(city:String, isSelected:Boolean, onTap: ()->Unit){

  Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTap() }, // Simple tap tracking
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CardDefaults.cardColors().containerColor else CardDefaults.cardColors().disabledContainerColor
        )
      ) {

  Text(
    text = city,
    fontSize =28.sp,
    modifier= Modifier
    .padding(horizontal=18.dp, vertical=14.dp)
    .clickable { onTap() },

  )
}
}

@Composable
fun CityListScreen(
  cities: List<String>,
  onAddCity: (String)->Unit,
  onRemoveCity: (String)->Unit,
  modifier: Modifier= Modifier,
) {
  var selectedItem by remember { mutableStateOf("") }
  var newCityName by remember {mutableStateOf("")}
  // var deleteCityName by remember {mutableStateOf("")}

  Column(modifier=modifier.fillMaxSize()) {
    Row(modifier=Modifier.padding(16.dp)) {
      OutlinedTextField(
        value= newCityName,
        onValueChange= {newCityName = it},
        label = {Text("City name")},
        modifier= modifier.weight(1f),
      )
      Spacer(modifier=modifier.width(8.dp))
    }
      Button(
        onClick={
          if (newCityName.isNotBlank()) {
            onAddCity(newCityName)
            newCityName=""
          }
        }
      )
      {
        Text("Add City")
      }

      Button(
        onClick={
          if (selectedItem.isNotBlank()) {
            onRemoveCity(selectedItem)
            selectedItem = ""
          }
        }
      )
      {
        Text("Remove City")
      }

      LazyColumn(modifier=Modifier.fillMaxSize()) {
        items(cities) {city->
          CityRow(city=city, isSelected = city == selectedItem, onTap = {selectedItem=city})
      }
      }
    }
  }
