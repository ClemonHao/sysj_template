#!/bin/bash

`make SYSJ=coordinator`
(make run SYSJ=coordinator) &

`make SYSJ=filler_middleware`
(make run SYSJ=filler_middleware) &

`make SYSJ=capper_controller`
(make run SYSJ=capper_controller) &

`make SYSJ=rotater_controller`
(make run SYSJ=rotater_controller) &

`make SYSJ=conveyer_controller`
(make run SYSJ=conveyer_controller) &

`make SYSJ=filler_controller_A`
(make run SYSJ=filler_controller_A) &

`make SYSJ=filler_controller_B`
(make run SYSJ=filler_controller_B) &

`make SYSJ=filler_controller_C`
(make run SYSJ=filler_controller_C) &

`make SYSJ=filler_controller_D`
(make run SYSJ=filler_controller_D) &

