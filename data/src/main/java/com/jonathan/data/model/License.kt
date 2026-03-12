package com.jonathan.data.model


import kotlinx.serialization.Serializable

@Serializable
data class License (

   var key    : String? = null,
   var name   : String? = null,
   var spdxId : String? = null,
   var url    : String? = null,
   var nodeId : String? = null

)