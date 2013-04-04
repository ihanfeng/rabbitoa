package com.zhm.rabbit.oa.activiti.explorer;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhm.rabbit.oa.model.GridBean;
import com.zhm.rabbit.oa.model.GridResultBean;
import com.zhm.rabbit.oa.utils.Page;

@Controller
@SessionAttributes("cpath")
@RequestMapping(value = "/workflow/model")
public class ModelController {
 
  protected Logger logger = LoggerFactory.getLogger(getClass());
 
  @Autowired
  RepositoryService repositoryService;
 
  /**
   * 模型列表
   */
  @RequestMapping(value = "list")
  public ModelAndView modelList() {
    ModelAndView mav = new ModelAndView("/workflow/model-list");
    return mav;
  }
  @RequestMapping(value="listJson")
  public @ResponseBody GridResultBean listJson(@ModelAttribute("cpath")String cpath,Integer page,String searchField,String searchString,String searchOper,Integer rows,String sidx,String sord,ModelMap model)
  {
	  Integer totalRecords = (int) repositoryService.createModelQuery().count();
	  Page pg = new Page(page,rows,totalRecords,5);
	  if(page>pg.getTotalPages())
	  {
		  page=pg.getTotalPages();
	  }
	  page=page==0?1:page;
	  List<Model> list = repositoryService.createModelQuery().listPage((page-1)*rows, rows);
	  GridResultBean result = new GridResultBean();
	  result.setPage(page);
	  result.setRecords(totalRecords);
	  result.setTotal(pg.getTotalPages());
	  List<GridBean> rowsBean = new ArrayList<GridBean>();
	  for(int i=0;i<list.size();i++)
	  {
		  Model tmp=list.get(i);
		  GridBean tmp1 = new GridBean();
		  tmp1.setId(i);
		  List cell = new ArrayList();	
		  cell.add(tmp.getId());
		  cell.add(tmp.getKey());
		  cell.add(tmp.getName());
		  cell.add(tmp.getVersion());
		  cell.add(new DateTime(tmp.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
		  cell.add(new DateTime(tmp.getLastUpdateTime()).toString("yyyy-MM-dd HH:mm"));
		  cell.add(tmp.getMetaInfo());
		  StringBuffer sb = new StringBuffer();
		  sb.append("<a href='"+cpath+"/service/editor?id="+tmp.getId()+"' class='cmdField' title='编辑' target='_top'>编辑</a>");
		  sb.append("<a href='"+cpath+"/workflow/model/deploy/"+tmp.getId()+"' class='cmdField' title='部署'>部署</a>");
		  sb.append("<a href='"+cpath+"/workflow/model/export/"+tmp.getId()+"' class='cmdField' title='导出' target='_top'>导出</a>");
		  cell.add(sb.toString());
		  tmp1.setCell(cell);
		  rowsBean.add(tmp1);
	  }
	  result.setRows(rowsBean);
	  return result;
  }
 
  /**
   * 创建模型
   */
  @RequestMapping(value = "create")
  public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
          HttpServletRequest request, HttpServletResponse response) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode editorNode = objectMapper.createObjectNode();
      editorNode.put("id", "canvas");
      editorNode.put("resourceId", "canvas");
      ObjectNode stencilSetNode = objectMapper.createObjectNode();
      stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
      editorNode.put("stencilset", stencilSetNode);
      Model modelData = repositoryService.newModel();
 
      ObjectNode modelObjectNode = objectMapper.createObjectNode();
      modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
      description = StringUtils.defaultString(description);
      modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
      modelData.setMetaInfo(modelObjectNode.toString());
      modelData.setName(name);
      modelData.setKey(StringUtils.defaultString(key));
 
      repositoryService.saveModel(modelData);
      repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
 
      response.sendRedirect(request.getContextPath() + "/service/editor?id=" + modelData.getId());
    } catch (Exception e) {
      logger.error("创建模型失败：", e);
    }
  }
 
  /**
   * 根据Model部署流程
   */
  @RequestMapping(value = "deploy/{modelId}")
  public String deploy(@PathVariable("modelId") String modelId, RedirectAttributes redirectAttributes) {
    try {
      Model modelData = repositoryService.getModel(modelId);
      ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
      byte[] bpmnBytes = null;
      BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
      bpmnBytes = new BpmnXMLConverter().convertToXML(model);
      String processName = modelData.getName() + ".bpmn20.xml";
      Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"UTF-8").replace("UTF-8", "UTF8")).deploy();
      redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
    } catch (Exception e) {
      logger.error("根据模型部署流程失败：modelId={}", modelId, e);
      e.printStackTrace();
    }
    return "redirect:/workflow/model/list";
  }
 
  /**
   * 导出model的xml文件
   */
  @RequestMapping(value = "export/{modelId}")
  public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
    try {
      Model modelData = repositoryService.getModel(modelId);
      BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
      JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
      BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
      BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
      byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
 
      ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
      IOUtils.copy(in, response.getOutputStream());
      String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
      response.setHeader("Content-Disposition", "attachment; filename=" + filename);
      response.flushBuffer();
    } catch (Exception e) {
      logger.error("导出model的xml文件失败：modelId={}", modelId, e);
    }
  }
 
}
