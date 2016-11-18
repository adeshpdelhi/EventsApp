var express = require('express');
var clubRouter = express.Router();
var db = require('../models');

clubRouter.route('/')
.get(function (req, res, next) {
    console.log('procesing get');
    db.clubs.findAll({
        include:[{
                model: db.events,
                
            }
        ]
    }).then(function(clubs){
        console.log(JSON.stringify(clubs));
        res.json(clubs);
    })
    
})
.post(function (req, res, next) {
    console.log('processing post : '+ req.body);
    console.log(req.body);
    db.clubs.findOne({
        where:{
            clubId:req.body.clubId,
        }
    }).then(function(club){
        console.log(club);
        if(club==null)
        {
            console.log('found '+JSON.stringify(club));
            db.clubs.build(req.body).save().then(function(result){
                res.json(result);
            })
        }
        else{
            res.status(400);
            res.end('Already exists!')
        }
    })
    
})


clubRouter.route('/:clubId')
.get(function(req,res,next){
    console.log('procesing get');
    db.clubs.findOne({
        where:{
            clubId:req.params.clubId
        },
        include:[{
                model: db.events,
            }
        ]
    }).then(function(club){
        console.log(JSON.stringify(club));
        res.json(club);
    })
})

.put(function(req,res,next){
    console.log(req.body);
    db.clubs.update(
    req.body, {
            where:{
                clubId:parseInt(req.params.clubId,10)
            }
        }
    )
    .then(function (result) { 
        console.log('success club update');
        res.status(200);
        res.end('saved');
    }, function(rejectedPromiseError){
        console.log('failed club update');
        console.log(rejectedPromiseError);
        res.status(400);
        res.end('failed update')
    })
})

.delete(function(req,res,next){
    console.log('processing delete');
    db.clubs.destroy({
        where:{
            clubId: req.params.clubId
        }
    }).then(function(result){
        console.log("deleted successfully!");
        console.log(JSON.stringify(result));
        res.json(result);
    })
})
;
module.exports = clubRouter;