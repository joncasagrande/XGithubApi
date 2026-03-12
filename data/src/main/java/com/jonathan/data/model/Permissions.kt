package com.jonathan.data.model


import kotlinx.serialization.Serializable

@Serializable
data class Permissions (

  
 var admin    : Boolean? = null,
  
 var maintain : Boolean? = null,
  
 var push     : Boolean? = null,
  
 var triage   : Boolean? = null,
  
 var pull     : Boolean? = null

)