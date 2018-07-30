package com.yl.distribute.scheduler.resource.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.yl.distribute.scheduler.common.bean.HostInfo;
import com.yl.distribute.scheduler.common.bean.JobConf;

/**
 * 从pool中选择资源最多的一台机器提交任务
 *
 */
public class ResourceIdleStrategy implements ServerSelectStrategy{

    @Override
    public String getIdleServer(JobConf request,ResourceManager rm,String... lastFailedServers) {
        List<String> servers = rm.getPoolServers().get(request.getPoolPath());
        List<HostInfo> sortedServers = new ArrayList<HostInfo>();
        if(servers != null && servers.size() > 0){
            for(String server : servers) {
                if(rm.getResourceMap().get(server) != null) {
                    sortedServers.add(rm.getResourceMap().get(server));
                }
            }
            Collections.sort(sortedServers);
            if(sortedServers != null && sortedServers.size() > 0) {
                if(lastFailedServers == null || lastFailedServers.length == 0) {
                    return sortedServers.get(0).getHostName();
                }else {
                    //任务重试会选择没有失败并且资源最多的server,如果没有可用server就抛出异常
                    List<HostInfo> runningServers = sortedServers.stream().filter(
                            hostInfo -> !Arrays.asList(lastFailedServers).contains(hostInfo.getHostName()))
                            .collect(Collectors.toList());
                    if(runningServers != null && runningServers.size() > 0) {
                        return runningServers.get(0).getHostName();
                    }else {
                        throw new RuntimeException("找不到可用的服务器 "+ request.getJobId());
                    }
                }
                
            }
        }
        throw new RuntimeException("can not find availiable server");
    }
}
