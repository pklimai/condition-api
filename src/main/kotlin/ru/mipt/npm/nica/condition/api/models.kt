package ru.mipt.npm.nica.condition.api

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table


object RunPeriods : IntIdTable("run_period", "period_number") {
    //val period_number = integer("period_number").primaryKey().entityId()
    val start_datetime = datetime("start_datetime")
    val end_datetime = datetime("end_datetime")
}

class RunPeriod(period_number: EntityID<Int>) : Entity<Int>(period_number) {
    companion object : EntityClass<Int, RunPeriod>(RunPeriods)

    //val period_number by RunPeriods.period_number
    val start_datetime by RunPeriods.start_datetime
    val end_datetime by RunPeriods.end_datetime

    fun asSerializable() = with(this) {
        SerializableRunPeriod(id.value, start_datetime.toString(), end_datetime.toString())
    }
}

@Serializable
data class SerializableRunPeriod(
    val run_period: Int,
    val start_datetime: String,
    val end_datetime: String
)

//class DualInt(val a: Int, val b: Int): Comparable<DualInt> {
//    override fun compareTo(other: DualInt): Int {
//        return when {
//            (a > other.a) -> 1
//            (a < other.a) -> -1
//            else -> when {
//                (b > other.b) -> 1
//                (b < other.b) -> -1
//                else -> 0
//            }
//        }
//    }
//}

object Runs : Table("run_") {
    val period_number = integer("period_number").primaryKey().references(RunPeriods.id)
    val run_number = integer("run_number").primaryKey()
    val file_path = varchar("file_path", 200).uniqueIndex()
    val beam_particle = varchar("beam_particle", 10)
    val target_particle = varchar("target_particle", 10).nullable()
    val energy = double("energy").nullable()
    val start_datetime = datetime("start_datetime")
    val end_datetime = datetime("end_datetime").nullable()
    val event_count = integer("event_count").nullable()
    val field_voltage = double("field_voltage").nullable()
    val file_size = long("file_size").nullable()
    val geometry_id = integer("geometry_id").nullable()
    val file_md5 = varchar("file_md5", 32).nullable()
}

//class Run(period_number: EntityID<Int>) : Entity<Int>(period_number) {
//    companion object : EntityClass<Int, Run>(Runs)
//
//    val period_number by Runs.period_number
//    val run_number by Runs.run_number
//    val file_path by Runs.file_path
//    val beam_particle by Runs.beam_particle
//    val target_particle by Runs.target_particle
//    val energy by Runs.energy
//    val start_datetime by Runs.start_datetime
//    val end_datetime by Runs.end_datetime
//    val event_count by Runs.event_count
//    val field_voltage by Runs.field_voltage
//    val file_size by Runs.file_path
//    val geometry_id by Runs.geometry_id
//    val file_md5 by Runs.file_md5
//}

@Serializable
data class SerializableRun(
    val period_number: Int,
    val run_number: Int,
    val file_path: String,
    val beam_particle: String,
    val target_particle: String?,
    val energy: Double?,
    val start_datetime: String,
    val end_datetime: String?,
    val event_count: Int?,
    val field_voltage: Double?,
    val file_size: Long?,
    val geometry_id: Int?,
    val file_md5: String?
)

