const express = require('express');
const router = express.Router();

module.exports = router;
router.get('/', (req, res)=>{
    res.send('vao api mobile');
})
const COMMON = require('./COMMON');
const uri = COMMON.uri;
const mongoose = require('mongoose');
const productModel = require('./productModel');
router.get('/list', async (req, res)=>{
    await mongoose.connect(uri);

    let products = await productModel.find();
    console.log(products);
    res.send(products);
});
router.post('/add', async (req, res)=>{
    await mongoose.connect(uri);    

    let product = req.body;
    let kq = await productModel.create(product);
    console.log(kq);

    let products = await productModel.find();
    console.log(products);
    res.send(products);
})
router.delete('/xoa/:id', async (req, res)=>{
    try{
        await mongoose.connect(uri);
    
        let id = req.params.id;
        console.log(id);
    
        const result = await productModel.deleteOne({_id: id});

    
        if (result) {
            let products = await productModel.find();
            console.log(products);
            res.send(products);
          } else {
            res.send('Xóa không thành công');
          }
        } catch (error) {
            console.error('Lỗi khi xóa:', error);
            res.send('Lỗi khi xóa');
          }
});
router.put('/update/:id', async (req, res)=>{
    try {
        const id = req.params.id;
        const data = req.body;

        await mongoose.connect(uri);
        console.log('Kết nối DB thành công');

        const result = await productModel.findByIdAndUpdate(id, data);

        if (result) {
            let products = await productModel.find();
            console.log(products);
            res.send(products);
        } else {
            res.send('Không tìm thấy sản phẩm để cập nhật');
        }
    } catch (error) {
        console.error('Lỗi khi cập nhật:', error);
        res.send('Lỗi khi cập nhật');
    }
});
