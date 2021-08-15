package ru.mipt.npm.nica.condition.api

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


object RunPeriods : Table("run_period") {
    val period_number = integer("period_number").primaryKey()
    val start_datetime = datetime("start_datetime")
    val end_datetime = datetime("end_datetime")
}

@Serializable
data class SerializableRunPeriod(
    val period_number: Int,
    val start_datetime: String,
    val end_datetime: String
)

object Runs : Table("run_") {
    val period_number = integer("period_number").primaryKey().references(RunPeriods.period_number)
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
