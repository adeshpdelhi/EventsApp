/* jshint indent: 2 */
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('clubs', {
          clubId: {
            type: DataTypes.BIGINT,
            autoIncrement: true,
            allowNull: false,
            unique: true,
            primaryKey: true
          },
          name: {
            type: DataTypes.STRING,
            allowNull: false,
          },
          // admins: {
          //   type: DataTypes.STRING,
          //   references:{
          //     model: 'users',
          //     key: 'email'
          //   }
          // },
          // associated_events: {
          //   type: DataTypes.BIGINT,
          //   references:{
          //     model: 'events',
          //     key: 'eventId'
          //   }
          // },
          // subscribers: {
          //   type: DataTypes.STRING,
          //   references:{
          //     model: 'users',
          //     key: 'email'
          //   }
          // }
  }, {
    tableName: 'clubs'
  });
};

