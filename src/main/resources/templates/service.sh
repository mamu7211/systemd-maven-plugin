#!/usr/bin/env bash

# ${project.artifactId} v${project.version}
#
# ${run.fileName} generated by @project.artifactId@ v@project.version@ at ${system.isoDate}
#
# Usage:
#
#   ./service.sh start
#   ./service.sh stop
#   ./service.sh restart
#   ./service.sh install
#   ./service.sh uninstall
#   ./service.sh status
#

SERVICE_HOME=${install.directory}
UNIT_NAME="${unit.file}.service"
UNIT_FILE="/etc/systemd/system/\${UNIT_NAME}"

echo "Service \${UNIT_NAME}."

if [ -z "$1" ]
  then
    echo "No argument supplied, use start|stop|restart|install|uninstall|status"
    exit 0
fi

function start_service {
	if systemctl is-active --quiet "\${UNIT_NAME}" ; then
		echo "Service '\${UNIT_NAME}'' is already active."
	else
		systemctl start "$UNIT_NAME" || exit 1
		echo "Service '\${UNIT_NAME}' started."
	fi
}

function stop_service {
	if systemctl is-active --quiet "\${UNIT_NAME}" ; then
		echo "Service '\${UNIT_NAME}'' is currently active. Trying to stop."
		systemctl stop "\${UNIT_NAME}" || exit 1
		echo "Service '\${UNIT_NAME}' was stopped."
	else
		echo "Service '\${UNIT_NAME}' is not active."
	fi
}

function status_service {
	systemctl status "\${UNIT_NAME}"
}

function copy_unit_file {
	cp "\${UNIT_NAME}" "\${UNIT_FILE}" || exit 1
	echo "Copied unit file."
}

function daemon_reload {
	systemctl daemon-reload || exit 1
	echo "Daemon reloaded."
}

function uninstall_service {
	if [ -f  "\${UNIT_FILE}" ]; then
		rm "\${UNIT_FILE}" || exit 1
		systemctl daemon-reload
	fi
}

# ---------------------------------------------------------

if [ "$1" = "start" ] ; then
	if [ -f  "\${UNIT_FILE}" ]; then
		start_service
	else
		echo "Cannot start service, '\${UNIT_FILE}' does not exist."
		exit 1
	fi
fi

if [ "$1" = "stop" ] ; then
	if [ -f  "\${UNIT_FILE}" ]; then
		stop_service
	else
		echo "Cannot stop service, '\${UNIT_FILE}' does not exist."
		exit 1
	fi
fi

if [ "$1" = "status" ] ; then
	if [ -f  "\${UNIT_FILE}" ]; then
		status_service
	else
		echo "Cannot get service status, '\${UNIT_FILE}' does not exist."
		exit 1
	fi
fi

if [ "$1" = "install" ] ; then
	if [ -f  "\${UNIT_FILE}" ]; then
		stop_service
		backup_unit_file
	fi
	copy_unit_file
fi

if [ "$1" = "uninstall" ] ; then
	if [ -f  "\${UNIT_FILE}" ]; then
		stop_service
		uninstall_service
	else
		echo "Cannot uninstall service, '\${UNIT_FILE}' does not exist."
		exit 1
	fi
fi

echo "'$1' for \${UNIT_NAME} is done."
