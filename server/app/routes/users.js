var express = require('express');
var userRouter = express.Router();
var db = require('../models');

userRouter.route('/')


.get(function (req, res, next) {
    console.log('procesing get');
    db.users.findAll({
        include:[{
            model: db.clubs,
            as: 'SubscribedClubs'
        },
        {
            model: db.events,
            as: 'SubscribedEvents'
        },
        {
            model: db.clubs,
            as: 'AdministeredClubs'
        },
        {
            model: db.events,
            as: 'AdministeredEvents'
        }
    ]
    }).then(function(users){
        console.log(JSON.stringify(users));
        res.json(users);
    });
    
})
.post(function (req, res, next) {
    console.log('processing post : '+ req.body);
    console.log(req.body);
    db.users.findOne({
        where:{
            email:req.body.email,
        }
    }).then(function(user){
        console.log(user);
        if(user==null)
        {
            console.log('found '+JSON.stringify(user));
            db.users.build(req.body).save().then(function(result){
                res.json(result);
            });
        }
        else{
            res.status(400);
            res.end('Already exists!')
        }
    });
    
})


userRouter.route('/:email')
.get(function(req,res,next){
    console.log('procesing get');
    db.users.findOne({
        where:{
            email:req.params.email
        },
        include:[{
            model: db.clubs,
            as: 'SubscribedClubs'
        },
        {
            model: db.events,
            as: 'SubscribedEvents'
        },
        {
            model: db.clubs,
            as: 'AdministeredClubs'
        },
        {
            model: db.events,
            as: 'AdministeredEvents'
        }
    ]
    }).then(function(user){
        console.log(JSON.stringify(user));
        res.json(user);
    });
})

.put(function(req,res,next){
    console.log(req.body);
    db.users.update(
    req.body, {
            where:{
                email:req.params.email
            }
        }
    )
    .then(function (result) { 
        db.users.findOne({
            where:{
                email:req.params.email
            }
        
        }).then(function(user){
            if(req.body.SubscribedClubs!=null)
                user.setSubscribedClubs(db.clubs.build(req.body.SubscribedClubs));
            if(req.body.SubscribedEvents!=null)
                user.setSubscribedEvents(db.events.build(req.body.SubscribedEvents));
            if(req.body.AdministeredClubs!=null)    
                user.setAdministeredClubs(db.clubs.build(req.body.AdministeredClubs));
            if(req.body.AdministeredEvents!=null)
                user.setAdministeredEvents(db.events.build(req.body.AdministeredEvents));
            console.log('success user update');
            res.status(200);
            res.json(result);
        });
        
    }, function(rejectedPromiseError){
        console.log('failed user update');
        res.status(400);
        res.end('failed update')
    });
})
;

// userRouter.route('/:email/subscribed_events/:eventId')
// .delete(function(req,res,next){
//     console.log('procesing delete');  
//     db.users.findOne({
//         where: {
//             email: req.params.email
//         }
//     }).then(function(user){
//         var where ={};
//         where.eventId = req.params.eventId;
//         user.getSubscribedEvents({where: where}).then(function(events){
//             event=events[0];
//             if(event!=null){
//                 console.log(JSON.stringify(event));
//                 user.removeSubscribedEvent(event).then(function () { 
//                     console.log('success event deleted');
//                     res.status(200);
//                     res.end('deleted');
//                 }, function(rejectedPromiseError){
//                     console.log('failed event delete');
//                     res.status(400);
//                     res.end('failed update');
//                 });
//             }
//             else{
//                 res.status(400);   
//                 res.end('Event not found');
//             }

//         }, function(rejectedPromiseError){
//             console.log(rejectedPromiseError);
//             res.status(400);   
//             res.end('Failed');

//         });
        
//     });
// })
// ;

// userRouter.route('/:email/subscribed_events')
// .get(function(req,res,next){
//     console.log('procesing get');
//     db.users.findOne({
//         where:{
//             email:req.params.email
//         },
//     }).then(function(user){
//         user.getSubscribedEvents().then(function(events){
//             console.log(JSON.stringify(events));
//             res.json(events);
//         })

//     });
// })
// .post(function (req, res, next) {
//     console.log('processing post : '+ req.body);
//     console.log(req.body);
//     db.users.findOne({
//         where:{
//             email: req.params.email
//         }
//     }).then(function(user){
//         if(user!=null)
//         {
//             console.log('found '+JSON.stringify(user));
//             db.events.findOne({
//                 where:{
//                     eventId: req.body.eventId
//                 }
//             }).then(function(event){
//                 if(event!=null)
//                 {
//                     console.log('found '+JSON.stringify(event));
//                     user.addSubscribedEvent(event);
//                     res.status(200);
//                     res.end('Added');
//                 }
//                 else
//                 {
//                     res.status(400);
//                     res.end('Event not found');
//                 }
//             });
//             }
//         else{
//             res.status(400);
//             res.end('User not found');
//         }
//     });
    
// })
// ;


// userRouter.route('/:email/subscribed_clubs/:clubId')
// .delete(function(req,res,next){
//     console.log('procesing delete');  
//     db.users.findOne({
//         where: {
//             email: req.params.email
//         }
//     }).then(function(user){
//         var where ={};
//         where.clubId = req.params.clubId;
//         user.getSubscribedClubs({where: where}).then(function(clubs){
//             club=clubs[0];
//             if(club!=null){
//                 console.log(JSON.stringify(club));
//                 user.removeSubscribedClub(club).then(function () { 
//                     console.log('success club deleted');
//                     res.status(200);
//                     res.end('deleted');
//                 }, function(rejectedPromiseError){
//                     console.log('failed club delete');
//                     res.status(400);
//                     res.end('failed update');
//                 });
//             }
//             else{
//                 res.status(400);   
//                 res.end('Club not found');
//             }

//         }, function(rejectedPromiseError){
//             console.log(rejectedPromiseError);
//             res.status(400);   
//             res.end('Failed');

//         });
        
//     });
// })
// ;

// userRouter.route('/:email/subscribed_clubs')
// .get(function(req,res,next){
//     console.log('procesing get');
//     db.users.findOne({
//         where:{
//             email:req.params.email
//         },
//     }).then(function(user){
//         user.getSubscribedClubs().then(function(clubs){
//             console.log(JSON.stringify(clubs));
//             res.json(clubs);
//         })

//     });
// })
// .post(function (req, res, next) {
//     console.log('processing post : '+ req.body);
//     console.log(req.body);
//     db.users.findOne({
//         where:{
//             email: req.params.email
//         }
//     }).then(function(user){
//         if(user!=null)
//         {
//             console.log('found '+JSON.stringify(user));
//             db.clubs.findOne({
//                 where:{
//                     clubId: req.body.clubId
//                 }
//             }).then(function(club){
//                 if(club!=null)
//                 {
//                     console.log('found '+JSON.stringify(club));
//                     user.addSubscribedClub(club);
//                     res.status(200);
//                     res.end('Added');
//                 }
//                 else
//                 {
//                     res.status(400);
//                     res.end('Club not found');
//                 }
//             });
//             }
//         else{
//             res.status(400);
//             res.end('User not found');
//         }
//     });
    
// })
// ;




module.exports = userRouter;