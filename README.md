

# CPermManager
A simple yet highly modular permission system for discord bots
| | |
|--|--|
|Lincense|[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/ctection/CPermManager/blob/master/LICENSE)|
|Support |[![Discord](https://img.shields.io/discord/554675435309629451?logo=discord)](https://discord.gg/eaZseHT2F7)|
|Downloads|[![Github all releases](https://img.shields.io/github/downloads/ctection/CPermManager/total.svg)](https://GitHub.com/ctection/CPermManager/releases/)|
|Build|![GitHub Workflow Status](https://img.shields.io/github/workflow/status/ctection/CPermManager/Gradle)|
## Download
|  |  |
|--|--|
|Latest Github Release|[![GitHub release](https://img.shields.io/github/release/ctection/CPermManager.svg)](https://GitHub.com/ctection/CPermManager/releases/)|
|Maven Central|[![Maven Central](https://img.shields.io/maven-central/v/com.ctection/CPermManager?color=gree)](https://search.maven.org/artifact/com.ctection/CPermManager/1.0.0/jar) |
|Commits|![GitHub commits since latest release (by SemVer)](https://img.shields.io/github/commits-since/ctection/CPermManager/1.0.0?color=gree&sort=date)|


**Be sure to replace VERSION with the version shown above!**
#### Maven
```XML
<dependencies>
	<dependency>
	  <groupId>com.ctection</groupId>
	  <artifactId>CPermManager</artifactId>
	  <version>VERSION</version>
	</dependency>
</dependencies>
```
#### Gradle
```gradle
dependencies {
	implementation 'com.ctection:CPermManager:VERSION'
}
```

## Getting Started
After you downloaded the CPermManager JAR, you can start implementing this library into your project.

First you have to create a PermissionManager object:
```Java
PermissionManager manager = new PermissionManager("MySQL Host", "SQL Port", "Database", 
"Username", "Password", "setname");
```
The *setname* argument is used to identify your PermissionSet, you can pick for example the name of your bot for that. 
### Configuring CPermManager
Now that you have a PermissionManager object you have to configure it once in order to use it. This means that you have to create Permissions and add them to your PermissionSet:
```Java
Permission p = new Permission("info.help");
Permission q = new Permission("info", "ping");
manager.addPermissionToSet(p,q);
```
As you can see there are two ways of creating a new permission: By providing the PermissionId and by providing the Permission Package and the Permission name seperated.
Once you added a permission to your PermissionSet the permission will be saved in the database, which means that CPermManager these permissions will be automatically inserted to the PermissionSet when you use the library another time.
### Using CPermManager
When you are done initializing everything, you can finally start using CPermManager. 
The current features of CPermManager are:

 - creating and managing custom Permissions
 - adding removing and setting a Users Permission
 - adding removing and setting a Roles Permission
 - checking Permissions of a single Entity(User/Role)
 - printing Permissions as ArrayLists
 - printing Permissions as JSON
 - printing Permissions as SCM (see [cSCM](https://github.com/ctection/cSCM))
 
 The methods are pretty self-explaining, but if you have questions about some of them feel free to ask.


#### Note: CPermManager can NOT run more than one instance at once, running more than one instance will cause errors!
