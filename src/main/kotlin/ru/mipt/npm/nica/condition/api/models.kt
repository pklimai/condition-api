package ru.mipt.npm.nica.condition.api

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntIdTable


object RunPeriods : IntIdTable("run_period", "period_number") {
    //val period_number = integer("period_number").primaryKey().entityId()
    val start_datetime = datetime("start_datetime")
    val end_datetime = datetime("end_datetime")
}

class RunPeriod(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, RunPeriod>(RunPeriods)

    //val period_number by RunPeriods.period_number
    val start_datetime by RunPeriods.start_datetime
    val end_datetime by RunPeriods.end_datetime

    fun asSerializable() = SerializableRunPeriod(
        this.id.value,
        this.start_datetime.toString(),
        this.end_datetime.toString()
    )
}

@Serializable
data class SerializableRunPeriod(
    val run_period: Int,
    val start_datetime: String,
    val end_datetime: String
)


