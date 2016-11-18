/* jshint indent: 2 */
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('users', {
          email: {
            type: DataTypes.STRING,
            allowNull: false,
            unique: true,
            primaryKey: true
          },
          name: {
            type: DataTypes.STRING
          },
          automaticSubscription: {
            type: DataTypes.BOOLEAN,
            default: false
          }
          // subscribed_clubs: {
          //   type: DataTypes.BIGINT,
          //   references:{
          //     model: 'clubs',
          //     key: 'clubId'
          //   }
          // },
          // subscribed_events: {
          //   type: DataTypes.BIGINT,
          //   references:{
          //     model: 'events',
          //     key: 'eventId'
          //   }
          // }
          
  }, {
    tableName: 'users'
  });
};

