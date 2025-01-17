package cn.spdb.harrier.api.controller.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.spdb.harrier.api.aspect.AccessLogAnnotation;
import cn.spdb.harrier.api.service.job.UdsJobWeightLimitService;
import cn.spdb.harrier.dao.entity.UdsJobWeightLimit;

@RestController
@RequestMapping("/udsJobWeightLimit")
public class UdsJobWeightLimitController {
    @Autowired
    private UdsJobWeightLimitService service;
    @AccessLogAnnotation(isDbInstall = true)
    @PutMapping("/add")
    public int add(UdsJobWeightLimit record) {
        return service.add(record);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @DeleteMapping("/delete")
    public int delete(Long id) {
        return service.delete(id);
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/update")
    public int update(UdsJobWeightLimit record) {
        return service.update(record);
    }

    @GetMapping("/getDetail")
    public UdsJobWeightLimit getDetail(Long id) {
        return service.getDetail(id);
    }

    @GetMapping("/listQuery")
    public List<UdsJobWeightLimit> listQuery() {
        return service.listQuery();
    }
    
    @GetMapping("/selectAll")
    public Map<String, Object> selectAll(Page<UdsJobWeightLimit> page, Integer limitType){
		Map<String, Object> result = new HashMap<String, Object>();
		Page<UdsJobWeightLimit> pageinfo = service.selectAll(page,limitType);
	    result.put("rows", pageinfo.getRecords());
		result.put("total", pageinfo.getTotal());
    	return result;
    }
    @AccessLogAnnotation(isDbInstall = true)
    @PostMapping("/updateWeightLimit")
    public int updateWeightLimit(Integer limit_type,Integer limit_value,String serverIds_,String timeWindows) {
    	return service.updateWeightLimit(limit_type,limit_value,serverIds_,timeWindows);
    }
    @AccessLogAnnotation(isDbInstall = true)
	@DeleteMapping("{ids}/delete")
	public int delete(@PathVariable(value = "ids") Long[] ids) {
		return service.delete(ids);
	}
}