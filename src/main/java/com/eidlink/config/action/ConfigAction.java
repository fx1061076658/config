package com.eidlink.config.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eidlink.config.common.GlobalConstant;
import com.eidlink.config.model.JsonKey;
import com.eidlink.config.model.PropertyBean;
import com.eidlink.config.model.ResultObject;
import com.eidlink.config.model.TreeNode;
import com.eidlink.config.utils.ObjUtils;
import com.eidlink.config.utils.StringUtil;
import com.eidlink.config.zk.ZKClient;
import com.eidlink.config.zk.ZKWatcher;
import com.eidlink.config.zk.acl.ZKIds;
import com.eidlink.config.zk.constant.ZKCreateModel;
import com.eidlink.config.zk.constant.ZKStat;

@Controller
public class ConfigAction {
  /**
   * 列表展示所有配置
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/list")
  @ResponseBody
  public ResultObject<String,List<PropertyBean>> list(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,List<PropertyBean>> resultObj = new ResultObject<String,List<PropertyBean>>();
    List<PropertyBean> propertyBeans = new ArrayList<PropertyBean>();
    String path = request.getParameter("path");
    path = StringUtil.isNotEmpty(path)?path:GlobalConstant.COMMON_DIR;
    ZKClient zkClient = new ZKClient(40000);
    List<String> dirs = zkClient.getChildren(path, true);
    for(String dir:dirs){
      String key = path+GlobalConstant.SYS_SEPARATOR+dir;
      ZKStat zkStat = zkClient.exists(key, true);
      Object str =  ObjUtils.byteArrayToObject(zkClient.getData(key, true, zkStat));
      PropertyBean pbean = new PropertyBean();
      pbean.set(dir, str);
      propertyBeans.add(pbean);
    }
    zkClient.close();
    resultObj.setSeccess(true);
    resultObj.setData(propertyBeans);
    return resultObj;
  }
  /**
   * save or update 配置
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/saveorupdate")
  @ResponseBody
  public ResultObject<String,Object> saveorupdate(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,Object> resultObj = new ResultObject<String,Object>();
    String path = request.getParameter("path");
    String key = request.getParameter("key");
    String okey = request.getParameter("okey");
    String value = request.getParameter("value");
    
    path = StringUtil.isNotEmpty(path)?path:GlobalConstant.COMMON_DIR;
    if(StringUtil.isAllNotEmpty(key,value)){
      ZKClient zkClient = new ZKClient( 40000);
//      if(StringUtil.isNotEmpty(okey)){
//        //先删除原始key
//        String orginConfKey = path+GlobalConstant.SYS_SEPARATOR+okey;
//        ZKStat ostat = zkClient.exists(orginConfKey, true);
//        if(ostat !=null ){
//          zkClient.delete(orginConfKey, ostat.getCversion(), false);
//        }
//      }
      //添加新key
      String confKey = path+GlobalConstant.SYS_SEPARATOR+key;
      ZKStat stat = zkClient.exists(confKey, true);
      if(stat !=null){
        resultObj.setMessage("key值重复！");
        zkClient.setData(confKey, value.getBytes(), -1);
        resultObj.setSeccess(false);
      }else{
        String result = zkClient.create(confKey, value.getBytes(),ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
        System.out.println(result);
        resultObj.setMessage("添加成功！");
        resultObj.setSeccess(true);
      }
    }
    
    return resultObj;
  }
  /**
   * 
   * 删除配置
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/del")
  @ResponseBody
  public ResultObject<String,Object> delConf(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,Object> resultObj = new ResultObject<String,Object>();
    String path = request.getParameter("path");
    String keys = request.getParameter("keys");
    path = StringUtil.isNotEmpty(path)?path:GlobalConstant.COMMON_DIR;
     
    ZKClient zkClient = new ZKClient( 40000);
    if(StringUtil.isAllNotEmpty(path,keys)){
      String[] keyArray = keys.split(",");
      for(String key : keyArray){
        String confKey = path+GlobalConstant.SYS_SEPARATOR+key;
        ZKStat stat = zkClient.exists(confKey, true);
        if(stat !=null){
          zkClient.delete(confKey, -1, false);
          resultObj.setMessage("删除成功！");
        }else{
          resultObj.setMessage("key值不存在！");
        }
       
      }
      resultObj.setSeccess(true);
    }
    return resultObj;
  }
  
  /**
   * 批量导入配置（若key值有冲突将直接替换原本值 ）慎用！！！
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/import")
  @ResponseBody
  public ResultObject<String,Object> importConf(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,Object> resultObj = new ResultObject<String,Object>();
    Map<String,String> map = new HashMap<String,String>();
    map.put("username", "sa");
    map.put("password", "sa");
    map.put("host", "10.0.6.129");
    map.put("db", "eidlink");
    resultObj.setSeccess(true);
    resultObj.setData(map);
    return resultObj;
  }
  
  /**
   * 切换zk服务器
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/switchZk")
  @ResponseBody
  public ResultObject<String,Object> switchZk(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,Object> resultObj = new ResultObject<String,Object>();
    String host = request.getParameter("host");
    String port = request.getParameter("port");
    if(StringUtil.isAllNotEmpty(host,port)){
      GlobalConstant.baseConf.put(JsonKey.ZK_HOST, host);
      GlobalConstant.baseConf.put(JsonKey.ZK_PORT, port);
      resultObj.setMessage("切换成功!");
    }else{
      resultObj.setMessage("参数错误！");
    }
    resultObj.setSeccess(true);
    return resultObj;
  }
  
  /**
   * 切换zk服务器
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/tree")
  @ResponseBody
  public ResultObject<String,List<TreeNode>> tree(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,List<TreeNode>> resultObj = new ResultObject<String,List<TreeNode>>();
    List<TreeNode> treeNodes = new ArrayList<TreeNode>();
    TreeNode root = new TreeNode();
    root.setId("-1");
    root.setText(GlobalConstant.ROOT_NAME);
    Map<String,String> rootMap = new HashMap<String,String>();
    rootMap.put("path", GlobalConstant.SYS_SEPARATOR+GlobalConstant.DEFAULT_ROOT);
    rootMap.put("value", GlobalConstant.DEFAULT_ROOT);
    root.setState("open");
    root.setAttributes(rootMap);
    Map<String,String> treeMap = GlobalConstant.defaultTree;
    Set<String> keySet = treeMap.keySet();
    List<TreeNode> childTree = new ArrayList<TreeNode>();
    List<String> keys = new ArrayList<String>();
    keys.addAll(keySet);
    for(int i =0;i<keys.size();i++){
      Map<String,String> attrMap = new HashMap<String,String>();
      
      TreeNode tree = new TreeNode();
      tree.setId(i+1+"");
      tree.setText(treeMap.get(keys.get(i)));
      attrMap.put("path", keys.get(i));
      attrMap.put("value", StringUtil.getPathValue(keys.get(i), GlobalConstant.SYS_SEPARATOR));
      tree.setState("open");
      tree.setAttributes(attrMap);
      childTree.add(tree);
    }
    root.setChildren(childTree);
    treeNodes.add(root);
    resultObj.setSeccess(true);
    resultObj.setData(treeNodes);
    return resultObj;
  }
  
  /**
   * 列表展示所有配置
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/init")
  @ResponseBody
  public ResultObject<String,List<PropertyBean>> init(HttpServletRequest request, HttpServletResponse response) {
    ResultObject<String,List<PropertyBean>> resultObj = new ResultObject<String,List<PropertyBean>>();
    List<PropertyBean> propertyBeans = new ArrayList<PropertyBean>();
    ZKClient zkClient = new ZKClient(40000);
    //创建root
    String root = GlobalConstant.DEFAULT_ROOT;
    zkClient.create(GlobalConstant.SYS_SEPARATOR+root, root.getBytes(), ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
    //创建app和common
    Map<String,String> treeMap = GlobalConstant.defaultTree;
    Set<String> keySet = treeMap.keySet();
    for(String key : keySet){
    	String value = StringUtil.getPathValue(key, GlobalConstant.SYS_SEPARATOR);
    	zkClient.create(key, value.getBytes(), ZKIds.OPEN_ACL_UNSAFE, ZKCreateModel.PERSISTENT);
    	
    }
    zkClient.close();
    resultObj.setSeccess(true);
    resultObj.setData(propertyBeans);
    return resultObj;
  }
}
