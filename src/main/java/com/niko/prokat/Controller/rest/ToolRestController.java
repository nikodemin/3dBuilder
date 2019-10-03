package com.niko.prokat.Controller.rest;

import com.niko.prokat.Model.dto.*;
import com.niko.prokat.Service.FileService;
import com.niko.prokat.Service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ToolRestController {
    private final ToolService toolService;
    private final FileService fileService;

    @PostMapping("/tool/{id}")
    public Integer addToolToCart(@PathVariable Long id, HttpSession session){
        OrderDto order =  (OrderDto) session.getAttribute("order");
        order.getTools().add(toolService.findToolById(id));
        session.setAttribute("order",order);

        return order.getUniqTools().size();
    }

    @DeleteMapping("/tool/{id}")
    public Integer deleteToolFromCart(@PathVariable Long id, HttpSession session){
        OrderDto order =  (OrderDto) session.getAttribute("order");
        final boolean[] firstDel = {true};
        order.getTools().removeIf(t->{
            if (firstDel[0] && t.getId().equals(id)){
                firstDel[0] = false;
                return true;
            }
            return false;
        });
        session.setAttribute("order",order);

        return order.getUniqTools().size();
    }

    @GetMapping("/cart-tools")
    public List<TulipDto<ToolDto,Integer>> getCartTools(HttpSession session){
        return ((OrderDto) session.getAttribute("order")).getUniqTools();
    }

    //admin panel
    @GetMapping("/admin/categories")
    public List<TreeNodeDto> getCategories(){
        return toolService.getCategoriesTree();
    }

    @GetMapping("/admin/categories/leafs")
    public List<CategoryDto> getCategoriesLeafs(){
        return toolService.getCategoriesLeafs();
    }

    @GetMapping("/admin/brands")
    public List<BrandDto> getBrands(){
        return toolService.getBrands();
    }

    @GetMapping("/admin/tools/category/{id}")
    public List<ToolDto> getTools(@PathVariable Long id){
        return toolService.findTools(id);
    }

    @DeleteMapping("/admin/category/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        if (id < 0) {
            return new ResponseEntity<>("Нельзя удалить категорию", HttpStatus.BAD_REQUEST);
        }
        toolService.removeCategory(id);
        return new ResponseEntity<>("Категория успешно удалена!", HttpStatus.OK);
    }

    @PutMapping("/admin/category/{id}/newName/{name}")
    public ResponseEntity renameCategory(@PathVariable Long id,
                                         @PathVariable String name){
        if (id < 0) {
            return new ResponseEntity<>("Нельзя переименовать категорию", HttpStatus.BAD_REQUEST);
        }
        toolService.renameCategory(id, name);
        return new ResponseEntity<>("Категория успешно переименована!",
                HttpStatus.OK);
    }

    @PostMapping("/admin/category/parent/{id}")
    public ResponseEntity addSubcategory(@PathVariable Long id,
                                         @ModelAttribute CategoryDto categoryDto) throws IOException {
        if (id == -2 || toolService.areThereToolsInCategory(id)){
            return new ResponseEntity<>("Нельзя добавить подкатегорию", HttpStatus.BAD_REQUEST);
        }
        MultipartFile[] files = {categoryDto.getFile()};
        String path = fileService.saveUploadedFiles(files).get(0);
        categoryDto.setImage(path);
        if (id == -1){
            categoryDto.setIsRoot(true);
            toolService.addCategory(categoryDto);
        } else {
            toolService.addSubCategory(categoryDto, id);
        }
        return new ResponseEntity<>("Категория успешно переименована!",
                HttpStatus.OK);
    }

    @PostMapping("/admin/tools/category/{id}")
    public ResponseEntity addTool(@PathVariable Long id,
                                  @ModelAttribute ToolDto toolDto) throws IOException {
        if (id < 0) {
            return new ResponseEntity<>("Нельзя добавить инструмент в данную категорию",
                    HttpStatus.BAD_REQUEST);
        }
        saveImages(toolDto);
        toolService.addTool(toolDto);
        return new ResponseEntity<>("Инструмент успешно добавлен!",
                HttpStatus.OK);
    }

    @PutMapping("/admin/tool/detach/{id}")
    public ResponseEntity detachTool(@PathVariable Long id){
        toolService.detachTool(id);
        return new ResponseEntity<>("Инструмент успешно отсоединён!",
                HttpStatus.OK);
    }

    @DeleteMapping("/admin/tool/{id}")
    public ResponseEntity deleteTool(@PathVariable Long id){
        toolService.removeTool(id);
        return new ResponseEntity<>("Инструмент успешно удалён!",
                HttpStatus.OK);
    }

    @PutMapping("/admin/tool/{id}")
    public ResponseEntity updateTool(@PathVariable Long id,
                                     @ModelAttribute ToolDto toolDto) throws IOException {
        ToolDto oldTool = toolService.getTool(id);
        toolDto.setPrevImagePath(oldTool.getPrevImagePath());
        toolDto.setImage1Path(oldTool.getImage1Path());
        toolDto.setImage2Path(oldTool.getImage2Path());
        toolDto.setImage3Path(oldTool.getImage3Path());
        saveImages(toolDto);
        toolService.updateTool(id,toolDto);
        return new ResponseEntity<>("Инструмент успешно обновлён!",
                HttpStatus.OK);
    }

    private void saveImages(ToolDto toolDto) throws IOException {
        MultipartFile[] files = new MultipartFile[4];
        files[0] = toolDto.getPrevImage();
        for (int i = 1; i < toolDto.getImages().length+1; i++) {
            files[i] = toolDto.getImages()[i-1];
        }
        List<String> paths = fileService.saveUploadedFiles(files);
        if (paths.isEmpty()){
            return;
        }
        toolDto.setPrevImagePath(paths.get(0));
        switch (paths.size()){
            case 4:
                toolDto.setImage3Path(paths.get(3));
            case 3:
                toolDto.setImage2Path(paths.get(2));
            case 2:
                toolDto.setImage1Path(paths.get(1));
        }
    }
}
