package org.rio.bookslauncher

import com.orm.SugarRecord
import com.orm.dsl.Unique

/**
 * Created by rio.chandra.r on 13/11/18.
 */

class SUGARS(): SugarRecord() {
    @Unique var key: String? = null
    var count: Int? = null

    constructor(key: String, count: Int) : this() {
        this.key = key
        this.count = count
    }

    fun createNew(key: String) {
        SUGARS(key, 0).save()
    }

    fun getCount(key: String): Int {
        val record = SugarRecord.find(SUGARS::class.java, "key = '${key}'")
        if(record.size == 0){
            this.createNew(key)
            return 0
        } else {
            val singlerec = record.get(0)
            return singlerec.count!!
        }
    }

    fun increment(key: String) {
        val record = SugarRecord.find(SUGARS::class.java, "key = '${key}'")
        if(record.size == 0){
            this.createNew(key)
        } else {
            val singlerec = record.get(0)
            singlerec.count = singlerec.count!! + 1
            singlerec.save()
        }
    }
}
