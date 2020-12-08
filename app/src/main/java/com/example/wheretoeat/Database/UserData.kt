package com.example.wheretoeat.Database

import android.graphics.Picture
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.wheretoeat.fragments.API.RestaurantData

@Entity(tableName = "user")
data class UserData(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "phone") val phone: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "picture",typeAffinity = ColumnInfo.BLOB) val picture: ByteArray?,

//    @Ignore val favorites: ArrayList<RestaurantData>?,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserData

        if (uid != other.uid) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (address != other.address) return false
        if (picture != null) {
            if (other.picture == null) return false
            if (!picture.contentEquals(other.picture)) return false
        } else if (other.picture != null) return false
        if (phone != other.phone) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (picture?.contentHashCode() ?: 0)
//        result = 31 * result + (favorites?.hashCode() ?: 0)
        return result
    }
}
