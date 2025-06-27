package co.uk.bbk.culinarycompanion_francispernecita

sealed class RecipeListItem {
    // Sealed class to represent different types of recipe list items
    data class RecipeItem(val recipe: Recipe) : RecipeListItem()
    data class CategoryHeader(val category: String) : RecipeListItem()
}