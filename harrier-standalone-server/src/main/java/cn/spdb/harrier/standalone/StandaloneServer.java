package cn.spdb.harrier.standalone;

import cn.spdb.harrier.api.ApiServer;
import cn.spdb.harrier.server.ServerApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import cn.spdb.harrier.alarm.AlarmServer;
import cn.spdb.harrier.api.MonitorApplication;
import cn.spdb.harrier.server.master.UdsMasterServer;
import cn.spdb.harrier.server.worker.UdsWorkerServer;

@SpringBootApplication
public class StandaloneServer {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiServer.class,AlarmServer.class,UdsWorkerServer.class, UdsMasterServer.class).run(args);
	}
}
