package to.msn.wings.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    init {
        fetchCategories()
    }

     private fun fetchCategories() {
         viewModelScope.launch {
             try {
                 val response = recipeService.getCategories()
                 _categoriesState.value = _categoriesState.value.copy(
                     loading = false,
                     list = response.categories,
                     error = null
                 )
             } catch (e: Exception) {
                 _categoriesState.value = _categoriesState.value.copy(
                     loading = false,
                     error = "データ取得エラー：${e.message}"
                 )
             }
         }
     }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}