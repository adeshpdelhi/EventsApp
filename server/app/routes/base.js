var express = require('express');

var apiRouter  = express.Router();

var usersRouter = require('./users');
var eventsRouter = require('./events');
var clubsRouter = require('./clubs');

apiRouter.use('/users',usersRouter);
apiRouter.use('/events',eventsRouter);
apiRouter.use('/clubs',clubsRouter);

module.exports = apiRouter;