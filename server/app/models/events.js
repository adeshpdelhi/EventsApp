/* jshint indent: 2 */
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('events', {
          eventId: {
            type: DataTypes.BIGINT,
            autoIncrement: true,
            primaryKey: true
          },
          name: {
            type: DataTypes.STRING,
            allowNull: false
          },
          associated_club: {
            type: DataTypes.BIGINT,
            references:{
              model: 'clubs',
              key: 'clubId'
            }
          },
          date: {
            type: DataTypes.DATE
          },
          description: {
            type: DataTypes.STRING
          },
          venue: {
            type: DataTypes.STRING
          },
          announcements: {
            type: DataTypes.STRING,
            get:function(){
              if(this.getDataValue('announcements') != null)
                return this.getDataValue('announcements').split('&&');
              else 
                return [];
            },
            set:function(val){
              console.log("setting: "+val);
              if(val == null)
              {
                this.setDataValue('announcements',val);
                return;
              }
              var value = val[0], split="&&";
              for(var i = 1; i< val.length;i++) 
                value = value.concat(split,val[i]);
              console.log(value);
              this.setDataValue('announcements',value);
            }
          },
          // subscribers: {
          //   type: DataTypes.STRING,
          //   references:{
          //     model: 'users',
          //     key: 'email'
          //   }
          // },
          // organizers: {
          //   type: DataTypes.STRING,
          //   references:{
          //     model: 'users',
          //     key: 'email'
          //   }
          // }
  }, {
    tableName: 'events'
  });
};

