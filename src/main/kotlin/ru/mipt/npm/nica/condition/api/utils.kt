package ru.mipt.npm.nica.condition.api

import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toSerializableRun() = SerializableRun(
    period_number = this[Runs.period_number],
    run_number = this[Runs.run_number],
    file_path = this[Runs.file_path],
    beam_particle = this[Runs.beam_particle],
    target_particle = this[Runs.target_particle],
    energy = this[Runs.energy],
    start_datetime = this[Runs.start_datetime].toString(),
    end_datetime = this[Runs.end_datetime].toString(),
    event_count = this[Runs.event_count],
    field_voltage = this[Runs.field_voltage],
    file_size = this[Runs.file_size],
    geometry_id = this[Runs.geometry_id],
    file_md5 = this[Runs.file_md5]
)

fun ResultRow.toSerializableRunPeriod() = SerializableRunPeriod(
    period_number = this[RunPeriods.period_number],
    start_datetime = this[RunPeriods.start_datetime].toString(),
    end_datetime = this[RunPeriods.end_datetime].toString()
)
