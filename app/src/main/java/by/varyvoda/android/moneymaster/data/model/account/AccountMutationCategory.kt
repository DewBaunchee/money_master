package by.varyvoda.android.moneymaster.data.model.account

data class AccountMutationCategory(
    val id: Long,
    val name: String,
    val icon: Int,
    val color: Int
) {
    companion object {
        val Unknown = AccountMutationCategory(
            id = 1,
            name = "Unknown",
            icon = 0,
            color = 0
        )
    }
}