var express = require('express');
var eventRouter = express.Router();
var db = require('../models');

eventRouter.route('/')
.get(function (req, res, next) {
    console.log('procesing get');
    db.events.findAll({
        include: [{
                model: db.clubs
            },{
                model: db.users,
                as: 'Admins',
                attributes: ['email','name']
            },
            {
                model: db.users,
                as: 'Subscribers',
                attributes: ['email']
            }
        ]
    }).then(function(events){
        console.log(JSON.stringify(events));
        res.json(events);
    })
    
})
.post(function (req, res, next) {
    console.log('processing post : '+ req.body);
    console.log(req.body);
    db.events.findOne({
        where:{
            eventId:req.body.eventId,
        }
    }).then(function(event){
        console.log(event);
        if(event==null)
        {
            console.log('found '+JSON.stringify(event));
            db.events.build(req.body).save().then(function(result){
                res.json(result);
            })
        }
        else{
            res.status(400);
            res.end('Already exists!')
        }
    })
    
})

eventRouter.route('/:eventId/announcement')
.post(function (req, res, next) {
    console.log('processing post : '+ req.body);
    console.log(req.body);
    db.events.findOne({
        where:{
            eventId:req.params.eventId,
        }
    }).then(function(event){
        console.log(event);
        if(event == null)
        {
            res.status(400);
            res.end('Event not found!')
        }
        else
        {
            newannouncement = event.announcements;
            newannouncement.push(req.body.announcement);
            event.announcements=newannouncement;
            event.save().then(function(result){
                console.log("announcement added successfully!");
                console.log(JSON.stringify(result));
                res.json(result);
            }, function(rejectedPromiseError){
                console.log('failed announcements update');
                res.status(400);
                res.end('failed announcements update')
            })
        }
    
    })
})


eventRouter.route('/:eventId')
.get(function(req,res,next){
    console.log('procesing get');
    db.events.findOne({
        where:{
            eventId:req.params.eventId
        },
        include: [{
                model: db.clubs
            },{
                model: db.users,
                as: 'Admins',
                attributes: ['email', 'name']
            },
            {
                model: db.users,
                as: 'Subscribers',
                attributes: ['email']
            }
        ]
    }).then(function(event){
        console.log(JSON.stringify(event));
        res.json(event);
    })
})

.put(function(req,res,next){
    console.log(req.body);
    db.events.update(
    req.body, {
            where:{
                eventId:parseInt(req.params.eventId,10)
            }
        }
    )
    .then(function (result) { 
        console.log('success event update');
        res.status(200);
        res.end('saved');
    }, function(rejectedPromiseError){
        console.log('failed event update');
        console.log(rejectedPromiseError);
        res.status(400);
        res.end('failed update')
    })
})

.delete(function(req,res,next){
    console.log('processing delete');
    db.events.destroy({
        where:{
            eventId: req.params.eventId
        }
    }).then(function(result){
        console.log("deleted successfully!");
        console.log(JSON.stringify(result));
        res.json(result);
    })
})
;
module.exports = eventRouter;