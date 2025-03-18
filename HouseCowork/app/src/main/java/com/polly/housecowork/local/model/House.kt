package com.polly.housecowork.local.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "houses")
data class House(
    @PrimaryKey
    val houseid: Int,
    val name: String,
    val description: String,
    val rules: String,
    @Embedded
    val member: HouseMember
) : Parcelable

@Parcelize
data class HouseMember(
    @PrimaryKey
    val hmid: Int,
    val member: Int,
    val house: Int
) : Parcelable


class HouseMemberTypeConverter {
    private val moshi = Moshi.Builder().build()
    private val memberAdapter = moshi.adapter(HouseMember::class.java)


    @TypeConverter
    fun String.toHouseMember(): HouseMember {
        return memberAdapter.fromJson(this) ?: throw IllegalArgumentException("Invalid member data")
    }
}