package org.merizen.protouser

import net.liftweb.record.{MegaProtoUser, MetaMegaProtoUser => UnderlyingMetaMegaProtoUser}

trait MetaMegaProtoUser[ModelType <: MegaProtoUser[ModelType]] extends UnderlyingMetaMegaProtoUser[ModelType] with ProtoUser {
  self: ModelType =>
}
