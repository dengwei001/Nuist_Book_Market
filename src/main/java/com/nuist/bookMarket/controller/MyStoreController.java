package com.nuist.bookMarket.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nuist.bookMarket.model.User;
import com.nuist.bookMarket.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/myStore")
public class MyStoreController {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private CombineService combineService;
    @Autowired
    private SchoolBookService schoolBookService;
    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private NovelService novelService;
    @Autowired
    private ParamAdminService paramAdminService;


    @RequestMapping("/upLoadBook")
    @ResponseBody
    public void upLoadBook(@RequestParam("file")MultipartFile file, @RequestParam Map param, HttpServletRequest request) throws Exception {
        if(!file.isEmpty()){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            String bookId = sequenceService.getBookId();
            param.put("BOOK_ID",bookId);
            param.put("SELLER",user.getName());
            param.put("SELLER_ID",user.getUserid());
            String imageName = bookId+"_img.jpg";
            param.put("IMAGE","./bookImages/"+imageName);
            param.put("BIGIMAGE","./bookImages/"+imageName);
            Map press = paramAdminService.getPressByCode((String) param.get("PRESS"));
            param.put("PRESS",press.get("PRESS"));
            if (param.containsKey("COLLEGE")){
                param.put("COLLEGE",paramAdminService.getCollegeByCode((String)param.get("COLLEGE")).get("COLLEGE"));
                param.put("SPECIALTY",paramAdminService.getSpecialtyByCode((String)param.get("SPECIALTY")).get("SPECIALTY"));
                schoolBookService.insertSchoolBook(param);
                combineService.insertDetail(param);
            }
            if (param.containsKey("TYPE")){
                param.put("TYPE",paramAdminService.getTypeByCode((String)param.get("TYPE")).get("TYPE"));
                referenceService.insertReference(param);
                combineService.insertDetail(param);
            }
            if (param.containsKey("STYLE")){
                param.put("STYLE",paramAdminService.getStyleByCode((String)param.get("STYLE")).get("STYLE"));
                novelService.insertNovel(param);
                combineService.insertDetail(param);
            }
            String path = request.getSession().getServletContext().getRealPath("/");
            System.out.println(path);
            file.transferTo(new File(path+"/WEB-INF/classes/static/bookImages/"+imageName));

        }
    }

    @RequestMapping("/getMyBook")
    @ResponseBody
    public Object getMyBook(@RequestParam Map param){
        int page = Integer.parseInt((String) param.get("page"));
        int rows = Integer.parseInt((String) param.get("rows"));
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        param.put("SELLER_ID",user.getUserid());
        List list1 = schoolBookService.queryBySellerId(param);
        List list2 =  referenceService.queryBySellerId(param);
        List list3 =  novelService.queryBySellerId(param);
        list1.addAll(list2);
        list1.addAll(list3);
        int total = list1.size();
        if (page*rows>total){
            list1 = list1.subList((page-1)*rows,list1.size());
        }else {
            list1 = list1.subList((page-1)*rows,page*rows);
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",total);
        resultMap.put("rows",list1);
        return resultMap;
    }
}