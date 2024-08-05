const express = require('express');

const app = express();

const port = 3000;

const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true}));

app.listen(port, ()=>{
    console.log('Server run 3000');
});


const COMMON = require('./COMMON');
const uri = COMMON.uri;
const mongoose = require('mongoose');
const productModel = require('./productModel');
const apiMobile = require('./api');
app.use('/api', apiMobile);

app.get('/', async (req, res)=>{
    await mongoose.connect(uri);

    let products = await productModel.find();
    console.log(products);
    res.send(products);
});

app.post('/add_product', async (req, res)=>{
    await mongoose.connect(uri);    

    // let prodcut = {
    //     name: 'sp 4',
    //     price: 12000,
    //     status: false
    // };

    let product = req.body;
    let kq = await productModel.create(product);
    console.log(kq);

    let products = await productModel.find();
    console.log(products);
    res.send(products);

});
app.delete('/xoa/:id', async (req, res)=>{
    try{
    await mongoose.connect(uri);

    let id = req.params.id;
    console.log(id);

    const result = await productModel.deleteOne({_id: id});
    if (result) {
        res.send('Xóa thành công');
      } else {
        res.send('Xóa không thành công');
      }

    res.redirect('../');
    } catch (error) {
        console.error('Lỗi khi cập nhật:', error);
        res.send('Lỗi khi cập nhật');
      }
});
app.put('/update/:id', async (req, res) => {
    try {
      const id = req.params.id;
      const data = req.body;
  
      await mongoose.connect(uri);
      console.log('Kết nối DB thành công');
  
      const result = await productModel.findByIdAndUpdate(id, data);
      
      if (result) {
        res.send('Cập nhật thành công');
      } else {
        res.send('Không tìm thấy sản phẩm để cập nhật');
      }
    } catch (error) {
      console.error('Lỗi khi cập nhật:', error);
      res.send('Lỗi khi cập nhật');
    }
  });
