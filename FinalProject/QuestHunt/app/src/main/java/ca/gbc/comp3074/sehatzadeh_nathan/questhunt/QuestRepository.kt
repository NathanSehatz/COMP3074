package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Context

class QuestRepository(context: Context) {

    private val preferences = context.getSharedPreferences(
        "QuestHuntProgress",
        Context.MODE_PRIVATE
    )

    fun isChallengeCompleted(locationId: Int): Boolean {
        return preferences.getBoolean("location_$locationId", false)
    }

    fun completeChallenge(locationId: Int) {
        preferences.edit()
            .putBoolean("location_$locationId", true)
            .apply()
    }

    fun getCompletedCount(): Int {
        return (1..3).count { isChallengeCompleted(it) }
    }

    fun getCompletionPercentage(): Int {
        return getCompletedCount() * 100 / 3
    }

    fun getTeamMembers(): List<String> {
        val savedMembers = preferences.getString(
            "team_members",
            "Player One (Leader),Player Two,Player Three"
        ) ?: ""

        return savedMembers.split(",").filter { it.isNotBlank() }
    }

    fun addTeamMember(name: String) {
        val members = getTeamMembers().toMutableList()
        members.add(name)

        preferences.edit()
            .putString("team_members", members.joinToString(","))
            .apply()
    }

    fun removeTeamMember(name: String) {
        val members = getTeamMembers().toMutableList()
        members.remove(name)

        preferences.edit()
            .putString("team_members", members.joinToString(","))
            .apply()
    }
}
