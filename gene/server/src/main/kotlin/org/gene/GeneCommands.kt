package com.bftcom.saumi.server

import org.springframework.shell.core.CommandMarker
import org.springframework.shell.core.annotation.CliCommand
import org.springframework.stereotype.Service

@Service()
class GeneCommands : CommandMarker {

    @CliCommand(value = ["tt"])
    fun loadQdpConf() {

    }
}